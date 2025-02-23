package com.betagames.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.betagames.configuration.jwt.JwtFilter;

/**
 *
 * @author FabriniMarco
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // Filtro JWT per la gestione dell'autenticazione
    @Autowired
    JwtFilter jwtFilter;

    /**
     * 
     * Configura la catena di filtri di sicurezza per l'applicazione.
     * 
     * @param http L'oggetto HttpSecurity utilizzato per configurare la sicurezza.
     * @return La catena di filtri di sicurezza configurata.
     * @throws Exception Se si verifica un errore durante la configurazione.
     * 
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Disabilita la protezione CSRF
            .authorizeHttpRequests(request -> request
                    .requestMatchers("/rest/**").permitAll() // permesso a tutti da ovunque poi sistemare come sotto
                    
                    // .requestMatchers("/rest/public/**").permitAll() // Permette l'accesso a tutti gli utenti
                    // .requestMatchers("/rest/user/**").hasAnyRole("USER", "ADMIN") // Richiede il ruolo USER o ADMIN
                    // .requestMatchers("/rest/admin/**").hasRole("ADMIN") // Richiede il ruolo ADMIN
                    .anyRequest().authenticated()) // Richiede autenticazione per tutte le altre richieste
            // .formLogin(form -> form
            // .loginPage("/public/login") // Configurazione per la pagina di login
            // personalizzata
            // .defaultSuccessUrl("/books", true).permitAll()) // URL di successo dopo il
            // login
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Aggiunge il filtro JWT prima
                                                                                                  // del filtro di autenticazione
        // .logout(LogoutConfigurer::permitAll); // Configurazione per il logout
        return http.build(); // Costruisce e restituisce la catena di filtri di sicurezza
    } // securityFilterChain

}// class
