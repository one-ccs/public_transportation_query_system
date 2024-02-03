package com.example.publictransportationquerysystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    //     return  httpSecurity.authorizeHttpRequests(conf -> conf
    //         .requestMatchers("/api/auth/**").permitAll()
    //         .anyRequest().authenticated()
    //     )
    //     .formLogin(conf -> conf
    //         .loginProcessingUrl("api/auth/login")
    //     )
    //     .build();
    // }
}
