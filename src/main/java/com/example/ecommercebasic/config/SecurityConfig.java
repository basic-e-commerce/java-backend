package com.example.ecommercebasic.config;

import com.example.ecommercebasic.config.provider.emailpassword.AdminUserDetailsService;
import com.example.ecommercebasic.config.provider.emailpassword.CustomerUserDetailsService;
import com.example.ecommercebasic.config.provider.emailpassword.UsernamePasswordAuthenticationProvider;
import com.example.ecommercebasic.entity.user.Roles;
import com.example.ecommercebasic.filter.JwtValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {
    private final JwtValidationFilter jwtValidationFilter;
    private final CustomerUserDetailsService customUserDetailService;
    private final AdminUserDetailsService adminUserDetailService;

    public SecurityConfig(JwtValidationFilter jwtValidationFilter, CustomerUserDetailsService customUserDetailService, AdminUserDetailsService adminUserDetailService) {
        this.jwtValidationFilter = jwtValidationFilter;
        this.customUserDetailService = customUserDetailService;
        this.adminUserDetailService = adminUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(x->x
                        .requestMatchers(HttpMethod.GET,"/api/v1/product/secure").hasAuthority(Roles.ROLE_CUSTOMER.getAuthority())
                        .requestMatchers(HttpMethod.GET,"/api/v1/auth/a").hasAuthority(Roles.ROLE_ADMIN.getAuthority())
                        .requestMatchers(HttpMethod.GET,"/api/v1/auth/c").hasAuthority(Roles.ROLE_CUSTOMER.getAuthority())
                        .requestMatchers(HttpMethod.GET,"/api/v1/product/not-secure").permitAll()
                        .anyRequest().permitAll())
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration ccfg = new CorsConfiguration();
            ccfg.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:5173"));
            ccfg.setAllowedMethods(Collections.singletonList("*"));
            ccfg.setAllowCredentials(true);
            ccfg.setAllowedHeaders(Collections.singletonList("*"));
            ccfg.setExposedHeaders(List.of("Authorization"));
            ccfg.setMaxAge(3600L);
            return ccfg;
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Varsayılan AuthenticationManager'ı al
        AuthenticationManager defaultAuthManager = authenticationConfiguration.getAuthenticationManager();

        // Özel AuthenticationProvider'larınızı oluşturun
        List<AuthenticationProvider> customProviders = List.of(
                new UsernamePasswordAuthenticationProvider(customUserDetailService,adminUserDetailService, passwordEncoder())
        );

        // Özel provider'lar ile bir ProviderManager oluştur
        ProviderManager customAuthManager = new ProviderManager(customProviders);

        return authentication -> {
            try {
                // Öncelikle özel sağlayıcılar ile doğrulama yap
                return customAuthManager.authenticate(authentication);
            } catch (AuthenticationException e) {
                // Özel sağlayıcı başarısız olursa varsayılan AuthenticationManager'ı dene
                return defaultAuthManager.authenticate(authentication);
            }
        };
    }


    @Bean
    public static PasswordEncoder passwordEncoder() {  // circular bağımlılık oldugu için static yaparak ilk başta yüklenmesinşi sağladık26
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
