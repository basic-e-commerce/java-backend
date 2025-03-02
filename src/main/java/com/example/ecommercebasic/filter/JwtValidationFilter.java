package com.example.ecommercebasic.filter;

import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.entity.user.User;
import com.example.ecommercebasic.exception.TokenExpiredException;
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
        final String authHeader = request.getHeader(ApplicationConstant.JWT_HEADER);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authHeader.substring(7);
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        try {
            response.setHeader(ApplicationConstant.JWT_HEADER, jwt);
            Claims claims = jwtUtils.getClaims(jwt);
            Authentication authentication = createAuthentication(claims, request, response);
            if (authentication == null) return;
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("JWT validation successful for user: {}", claims.getSubject());
        } catch (ExpiredJwtException ex) {
            logger.error("JWT expired: {}", ex.getMessage());
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token expired", request);
            return;
        } catch (JwtException | IllegalArgumentException ex) {
            logger.error("Invalid JWT: {}", ex.getMessage());
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid token", request);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(Claims claims, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String username = claims.getSubject();
            User user = userService.getUserByUsername(username);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    user.getAuthorities()
            );
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            return auth;
        } catch (NullPointerException npe) {
            logger.error("User not found or token is invalid");
            writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token expired", request);
            return null; // Hata durumunda authentication oluşturma
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/api/v1/auth");
    }

    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message, HttpServletRequest request) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        String jsonResponse = String.format("""
        {
            "timestamp": "%s",
            "status": %d,
            "error": "%s",
            "message": "%s",
            "path": "%s"
        }
        """,
                java.time.Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
