package com.betagames.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author FabriniMarco
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }// PasswordEncoder

    /*
     * sistemare poi la configurazione, ora permesso a tutti
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> 
                authorize.requestMatchers("/**").permitAll()) // Permette l'accesso a tutti gli endpoint
            .csrf(csrf -> csrf.disable()); // Disabilita il CSRF per facilitare i test

        return http.build();
    }// SecurityFilterChain
    
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(authorize -> 
//            	authorize.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//                .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .requestMatchers("/public/**").permitAll()
//                .anyRequest().authenticated())
//            .csrf(csrf -> csrf.disable())
//            .httpBasic()
//            .and()
//            .userDetailsService(customUserDetailsService)  // Assicurati di iniettare il tuo CustomUserDetailsService
//            .passwordEncoder(passwordEncoder());
//
//        return http.build();
//    }
    
    
}// class
