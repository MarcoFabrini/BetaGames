package com.betagames.configuration.jwt;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

/**
 * La classe JwtProvider è responsabile della generazione e della gestione dei
 * token JWT (JSON Web Token).
 * I token JWT vengono utilizzati per l'autenticazione e l'autorizzazione degli
 * utenti in un'applicazione.
 * 
 * @author FabriniMarco
 */
@Component
public class JwtProvider {

    /*
     * Chiave segreta utilizzata per firmare i token JWT.
     * Viene generata utilizzando l'algoritmo di firma HMAC SHA-256.
     */
    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    /**
     * Genera un token JWT per un utente specifico.
     * 
     * @param username Il nome utente per il quale generare il token.
     * @return Il token JWT generato.
     */
    public String generateToken(String username) {
        // Calcola la data di scadenza del token, impostata a 2 ore nel futuro.
        var expirationDate = Date.from(LocalDateTime
                .now()
                .plusHours(2) // Aggiunge 2 ore all'ora attuale
                .toInstant(ZoneOffset.UTC)); // Converte in un oggetto Date

        // Costruisce e restituisce il token JWT
        return Jwts.builder()
                .subject(username) // Imposta il soggetto del token (il nome utente)
                .expiration(expirationDate) // Imposta la data di scadenza
                .signWith(key) // Firma il token con la chiave segreta
                .compact(); // Compatta il token in una stringa
    }// generateToken

    /**
     * Estrae il nome utente dal token JWT fornito.
     * 
     * @param token Il token JWT da cui estrarre il nome utente.
     * @return Il nome utente estratto dal token.
     */
    public String getUsernameFromToken(String token) {
        // Analizza il token JWT e verifica la firma utilizzando la chiave segreta
        var payload = Jwts
                .parser()
                .verifyWith(key) // Verifica la firma del token
                .build().parseSignedClaims(token).getPayload(); // Analizza il token e ottiene il payload

        // Restituisce il soggetto (nome utente) dal payload
        return payload.getSubject();
    }// getUsernameFromToken

} // class