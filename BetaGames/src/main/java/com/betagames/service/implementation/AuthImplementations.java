/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.betagames.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.betagames.configuration.jwt.JwtProvider;
import com.betagames.dto.TokenDTO;
import com.betagames.model.Users;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IAuthService;

/**
 *
 * @author vattelappesca
 */
@Service
public class AuthImplementations implements IAuthService {

    @Autowired
    IUsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;

    @Override
    public TokenDTO login(UsersRequest req) throws Exception {

        // Cerca l'utente nel repository in base al nome utente fornito
        Optional<Users> users = usersRepository.findByUsername(req.getUsername());

        // Verifica se l'utente esiste e se la password fornita corrisponde a quella
        // memorizzata
        if (!users.isPresent() || !passwordEncoder.matches(req.getPwd(), users.get().getPwd())) {
            throw new Exception("Invalid password or username");
        }

        // Genera il token JWT per l'utente autenticato
        TokenDTO token = new TokenDTO(jwtProvider.generateToken(users.get().getUsername()));

        return token;
    }// login

}// class
