/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.betagames.service.implementation;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.configuration.jwt.JwtProvider;
import com.betagames.dto.TokenDTO;
import com.betagames.model.Carts;
import com.betagames.model.Roles;
import com.betagames.model.Users;
import com.betagames.repository.ICartsRepository;
import com.betagames.repository.IRolesRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IAuthService;
import com.betagames.service.interfaces.IServiceMessagesService;

/**
 *
 * @author vattelappesca
 */
@Service
public class AuthImplementations implements IAuthService {

    @Autowired
    IUsersRepository usersRepository;
    @Autowired
	IServiceMessagesService serviceMessagesService;
    @Autowired
    IRolesRepository rolesRepository;
    @Autowired
    ICartsRepository cartsRepository;

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

    @Override
	@Transactional(rollbackFor = Exception.class)
	public void signin(UsersRequest req) throws Exception {
		Date now = new Date();

		Optional<Roles> roleUser = rolesRepository.findByNameIgnoreCase("user");
		Optional<Roles> roleAdmin = rolesRepository.findByNameIgnoreCase("admin");

		if (!roleUser.isPresent() || !roleAdmin.isPresent())
			throw new Exception(serviceMessagesService.getMessage("role-noPresent"));

		// Verifica se è il primo utente nel sistema
		boolean isFirstUser = !usersRepository.findTopBy().isPresent();

		if (usersRepository.findByUsername(req.getUsername()).isPresent())
			throw new Exception(serviceMessagesService.getMessage("user-Username"));

		if (usersRepository.findByEmail(req.getEmail()).isPresent())
			throw new Exception(serviceMessagesService.getMessage("user-email"));

		Users newUser = new Users();
		newUser.setUsername(req.getUsername());
		newUser.setEmail(req.getEmail());

		String hashedPassword = passwordEncoder.encode(req.getPwd());
		newUser.setPwd(hashedPassword);

		Carts cart = new Carts();
		cart.setUser(newUser);
		cart.setCreatedAt(now);
		cart.setUpdatedAt(now);
		cartsRepository.save(cart);

		newUser.setActive(true);
		newUser.setCart(cart);

		newUser.setRole(isFirstUser ? roleAdmin.get() : roleUser.get());

		usersRepository.save(newUser);
	}// signin
}// class
