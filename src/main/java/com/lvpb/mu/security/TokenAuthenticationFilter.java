package com.lvpb.mu.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/4 00:07
 * @description TokenAuthenticationFilter
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
}
