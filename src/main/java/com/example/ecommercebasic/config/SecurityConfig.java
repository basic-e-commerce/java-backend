package com.example.ecommercebasic.config;

import com.example.ecommercebasic.config.provider.emailpassword.CustomUserDetailsService;
import com.example.ecommercebasic.config.provider.emailpassword.UsernamePasswordAuthenticationProvider;
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
import org.springframework.security.core.Authentication;
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
    private final CustomUserDetailsService customUserDetailService;

    public SecurityConfig(JwtValidationFilter jwtValidationFilter, CustomUserDetailsService customUserDetailService) {
        this.jwtValidationFilter = jwtValidationFilter;
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(x->x
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
                new UsernamePasswordAuthenticationProvider(customUserDetailService, passwordEncoder())
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
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
