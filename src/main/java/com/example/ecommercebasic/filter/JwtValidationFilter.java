package com.example.ecommercebasic.filter;

import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.entity.user.User;
import com.example.ecommercebasic.service.auth.JwtUtils;
import com.example.ecommercebasic.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtValidationFilter.class);
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public JwtValidationFilter(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);

        if (jwt.startsWith("Bearer "))
            jwt = jwt.substring(7);

        try {
            response.setHeader(ApplicationConstant.JWT_HEADER, jwt);
            Claims claims = jwtUtils.getClaims(jwt);
            Authentication authentication = createAuthentication(claims, request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("JWT validation successful for user: {}", claims.getSubject());
        } catch (ExpiredJwtException ex) {
            logger.error("JWT expired: {}", ex.getMessage());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Token expired");
            return;
        } catch (JwtException | IllegalArgumentException ex) {
            logger.error("Invalid JWT: {}", ex.getMessage());
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
            return;
        }
        filterChain.doFilter(request,response);
    }

    private Authentication createAuthentication(Claims claims, HttpServletRequest request) {
        String username = claims.getSubject();
        User user = userService.getUserByUsername(username);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username,
                null,
                user.getAuthorities()
        );
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return auth;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/v1/auth");
    }
}
