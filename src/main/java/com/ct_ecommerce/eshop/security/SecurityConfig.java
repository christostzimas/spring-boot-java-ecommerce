package com.ct_ecommerce.eshop.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration of the security on endpoints.
 * @Value("${application.security.disabled:false}" ** points to application.properties
 * @return The chain built
 * @throws Exception Thrown on error configuring
 */
@Configuration
public class SecurityConfig {
    @Value("${application.security.disabled:false}")
    private boolean securityDisabled;

    private JWTRequestsFilter JWTRequestsFilter;

    public SecurityConfig(JWTRequestsFilter jWTRequestsFilter) {
        this.JWTRequestsFilter = jWTRequestsFilter;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //TODO: user levels for accessing specific routes (admin only)
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        /** Add filter manually before the Authentication Filter or anything that implements this type. */
        http.addFilterBefore(JWTRequestsFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests((authorize) ->
                authorize
                        // Specific exclusions or rules.
                        .requestMatchers(
                                "/api/products",
                                "/api/products/fridge",
                                "/api/products/mobile",
                                "/api/user/login",
                                "/api/user/register",
                                "/api/products/reviews/**"
                        ).permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

}