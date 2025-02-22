// Suggested code may be subject to a license. Learn more: ~LicenseLog:313263501.
package com.chatop.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.chatop.api.services.DbUserDetailsService;
import com.chatop.api.utils.JwtAuthenticationEntryPoint;
import com.chatop.api.utils.JwtRequestFilter;
import com.chatop.api.utils.JwtUtils;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private DbUserDetailsService dbUserDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // Configures the security filter chain for HTTP requests.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disables CSRF protection.
            .csrf(csrf -> csrf.disable())
            // Sets the custom authentication entry point for handling unauthorized access.
            .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            // Configures session management to be stateless.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Configures authorization rules for HTTP requests.
            .authorizeHttpRequests(auth ->
                auth
                    .requestMatchers("/api/auth/**").permitAll() 
                    .anyRequest().authenticated() // Requires authentication for any other request.
            );

        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(jwtUtils, dbUserDetailsService);
    }

}     
