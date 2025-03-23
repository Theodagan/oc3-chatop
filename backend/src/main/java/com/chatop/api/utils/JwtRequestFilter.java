package com.chatop.api.utils;

import com.chatop.api.services.DbUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private final JwtUtils jwtUtils;

    @Autowired
    private final DbUserDetailsService dbUserDetailsService;

    public JwtRequestFilter(JwtUtils jwtUtils, DbUserDetailsService dbUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.dbUserDetailsService = dbUserDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Extract JWT from header, validate, and set authentication
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtils.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.dbUserDetailsService.loadUserByUsername(username);

            if (jwtUtils.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
                SecurityContextHolder
                    .getContext()
                    .setAuthentication(usernamePasswordAuthenticationToken);
            
            }
        }
        chain.doFilter(request, response);
    }
}
