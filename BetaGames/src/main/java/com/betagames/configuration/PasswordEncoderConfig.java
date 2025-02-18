package com.betagames.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * @author FabriniMarco
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Crea e restituisce un'istanza di PasswordEncoder utilizzando l'algoritmo
     * BCrypt.
     * 
     * @return Un oggetto PasswordEncoder che utilizza BCrypt per la codifica delle
     *         password.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }// passwordEncoder

}// class
