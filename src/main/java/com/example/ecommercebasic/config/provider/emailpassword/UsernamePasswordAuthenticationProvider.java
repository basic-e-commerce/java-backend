package com.example.ecommercebasic.config.provider.emailpassword;

import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.entity.user.Roles;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.exception.UnAuthorizedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final CustomerUserDetailsService userDetailsService;
    private final AdminUserDetailsService adminUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public UsernamePasswordAuthenticationProvider(CustomerUserDetailsService userDetailsService, AdminUserDetailsService adminUserDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.adminUserDetailsService = adminUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            if (authentication.getDetails()== Roles.ROLE_ADMIN)
                userDetails = adminUserDetailsService.loadUserByUsername(username);
            else if (authentication.getDetails() == Roles.ROLE_CUSTOMER)
                userDetails = userDetailsService.loadUserByUsername(username);
            else
                throw new BadRequestException(ApplicationConstant.BAD_REQUEST);
        }else
            throw new BadRequestException(ApplicationConstant.BAD_PROVIDER);


        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UnAuthorizedException(ApplicationConstant.WRONG_CREDENTIALS);
        }

        return createSuccessAuthentication(username,authentication,userDetails);
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details
        UsernamePasswordAuthenticationToken result = UsernamePasswordAuthenticationToken.authenticated(principal,
                authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
