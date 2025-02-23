package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.TokenDTO;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseObject;
import com.betagames.service.interfaces.IAuthService;

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
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    IAuthService authService;
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

}// class
