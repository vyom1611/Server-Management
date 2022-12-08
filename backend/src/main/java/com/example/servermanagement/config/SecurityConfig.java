package com.example.servermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager user() {
        return new InMemoryUserDetailsManager(User
                .withUsername("vyom")
                .password("")
                .authorities("read")
                .build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); //Disable CSRF
        http.authorizeRequests(auth -> auth.anyRequest().authenticated()); //Make all requests to be authenticated
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Making a session stateless because we are not storing client data yet
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
