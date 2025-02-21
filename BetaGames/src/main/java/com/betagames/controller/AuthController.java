package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.betagames.configuration.jwt.JwtProvider;
import com.betagames.dto.TokenDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.model.Users;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseObject;
import com.betagames.service.interfaces.IAuthService;
import static com.betagames.utility.Utilities.buildUsersDTO;

/**
 * La classe AuthController gestisce le richieste di autenticazione degli
 * utenti.
 * Espone un endpoint per il login, dove gli utenti possono inviare le proprie
 * credenziali per ottenere un token JWT (JSON Web Token).
 * 
 * @author FabriniMarco
 */
@RestController
@RequestMapping("/rest/")
public class AuthController {

    @Autowired
    IAuthService authService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    IUsersRepository usersRepository;
    @Autowired
    Logger log;

    /**
     * Endpoint per il login degli utenti.
     * 
     * @param req Oggetto contenente le credenziali dell'utente (username e
     *            password).
     * @return Un ResponseEntity contenente il token JWT se l'autenticazione ha
     *         successo.
     * @throws Exception Se il nome utente o la password non sono validi.
     */
    @PostMapping("public/auth/login")
    public ResponseObject<TokenDTO> login(@RequestBody(required = true) UsersRequest req) throws Exception {
        ResponseObject<TokenDTO> responseToken = new ResponseObject<>();
        responseToken.setRc(true);

        try {
            responseToken.setData(authService.login(req));
        } catch (Exception e) {
            log.error(e.getMessage());
            responseToken.setMsg(e.getMessage());
            responseToken.setRc(false);
        }

        // Restituisce l'oggetto ResponseObject come risposta
        return responseToken;
    } // login

    @PostMapping("public/auth/signin")
    public ResponseBase signin(@RequestBody(required = true) UsersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
            authService.signin(req);
            response.setMsg("Successfully created user");
        } catch (Exception e) {
            log.error("Failed to create user " + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// signin

    /*
     * accetta in input il token
     * dal token deve tornare l'utente (UsersDTO corrispondente al token)
     */
    // @GetMapping("public/auth/me")
    // public UsersDTO me(@RequestHeader("Authorization") String tok) throws
    // Exception {
    // log.debug("RequestHeader Authorization: " + tok);
    // try {
    // String token = tok.substring(7);// Estrai il token rimuovendo "Bearer "
    // log.debug("token substring: " + token);

    // String username = jwtProvider.getUsernameFromToken(token);
    // log.debug("username from token: " + username);

    // Optional<Users> users = usersRepository.findByUsername(username);
    // Users u = users.get();
    // return buildUsersDTO(u);
    // } catch (Exception e) {
    // throw new Exception("User not found");
    // }
    // }// me

    @GetMapping("public/auth/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String tok) {
        if (tok == null || !tok.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( "JWT token missing or invalid");
        }

        try {
            // Rimuove il prefisso "Bearer "
            String token = tok.substring(7);
            //log.debug("Token ricevuto dopo substring: " + token);

            // Estrai l'username dal token
            String username = jwtProvider.getUsernameFromToken(token);
            //log.debug("Username estratto dal token: " + username);

            // Recupera l'utente dal DB
            Users user = usersRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            // Converte l'utente in DTO
            UsersDTO userDto = buildUsersDTO(user);

            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            log.error("Error during token validation", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }// me

}// class
