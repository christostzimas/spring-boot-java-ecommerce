package com.ct_ecommerce.eshop.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //TODO: Proper authentication.
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        http.authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll());
        return http.build();
    }

}