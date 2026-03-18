package com.eazybytes.jensenstore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PublicPathConfig {

    @Bean
    public List<String> publicPaths(){
        return List.of(
                "/api/v1/products/**",
                "/api/v1/contacts/**",
                "/api/v1/auth/login",     // 只有 Login 是公開的
                "/api/v1/auth/register",  // 只有 Register 是公開的
                "/error",
                "/api/v1/csrf-token"
        );
    }
}