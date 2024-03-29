package com.example.newsservice.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager manager(HttpSecurity http, UserDetailsService service, PasswordEncoder passwordEncoder) throws Exception {
        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(service);
        var authenticationProvider = new DaoAuthenticationProvider(passwordEncoder);
        authenticationProvider.setUserDetailsService(service);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http, AuthenticationManager manager) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/api/users")
                        .hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/user/role")
                        .hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/user")
                        .hasAnyRole("ADMIN", "MODERATOR", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/user")
                        .hasAnyRole("ADMIN", "MODERATOR", "USER")
                        .requestMatchers("/api/categories", "/api/categories/**")
                        .hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers(HttpMethod.GET, "/api/categories", "/api/categories/**")
                        .hasAnyRole("USER")
                        .requestMatchers("/api/news", "/api/news/**", "/api/comments", "/api/comments/**")
                        .hasAnyRole("ADMIN", "MODERATOR", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/users/user")
                        .permitAll().anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationManager(manager);

        return http.build();
    }
}
