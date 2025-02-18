package com.betagames.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.configuration.jwt.JwtProvider;
import com.betagames.model.Users;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.UsersRequest;

/**
 * La classe AuthController gestisce le richieste di autenticazione degli
 * utenti.
 * Espone un endpoint per il login, dove gli utenti possono inviare le proprie
 * credenziali per ottenere un token JWT (JSON Web Token).
 * 
 * @author FabriniMarco
 */
@RestController
@RequestMapping("/rest")
public class AuthController {

    // Repository per l'accesso ai dati degli utenti
    @Autowired
    private IUsersRepository usersRepository;

    // Codificatore di password utilizzato per verificare le password
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Provider per la generazione dei token JWT
    @Autowired
    private JwtProvider jwtProvider;

    /**
     * Endpoint per il login degli utenti.
     * @param req Oggetto contenente le credenziali dell'utente (username e
     *            password).
     * @return Un ResponseEntity contenente il token JWT se l'autenticazione ha
     *         successo.
     * @throws Exception Se il nome utente o la password non sono validi.
     */
    @PostMapping("/public/login")
    public ResponseEntity<String> login(@RequestBody(required = true) UsersRequest req) throws Exception {
        // Cerca l'utente nel repository in base al nome utente fornito
        Optional<Users> users = usersRepository.findByUsername(req.getUsername());

        // Verifica se l'utente esiste e se la password fornita corrisponde a quella
        // memorizzata
        if (!users.isPresent() || !passwordEncoder.matches(req.getPwd(), users.get().getPwd())) {
            throw new Exception("Invalid password or username"); // Lancia un'eccezione se le credenziali non sono
                                                                 // valide
        }

        // Genera il token JWT per l'utente autenticato
        String token = jwtProvider.generateToken(users.get().getUsername());
        return ResponseEntity.ok(token); // Restituisce il token JWT come risposta
    } // login

}// class
