package com.betagames.service.implementation;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.UsersDTO;
import com.betagames.model.Roles;
import com.betagames.model.Users;
import com.betagames.repository.IRolesRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IServiceMessagesService;
import com.betagames.service.interfaces.IUsersService;
import static com.betagames.utility.Utilities.buildUsersDTO;

/**
 *
 * @author FabriniMarco
 */
@Service
public class UsersImplementation implements IUsersService {

	@Autowired
	IServiceMessagesService serviceMessagesService;

	private final Logger log;
	private final IUsersRepository usersRepository;
	private final IRolesRepository rolesRepository;
	private final PasswordEncoder passwordEncoder;

	public UsersImplementation(Logger log, IUsersRepository usersRepository,
			IRolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
		this.log = log;
		this.usersRepository = usersRepository;
		this.rolesRepository = rolesRepository;
		this.passwordEncoder = passwordEncoder;
	}// costructor

	@Override
	public List<UsersDTO> list() throws Exception {
		List<Users> listUsers = usersRepository.findAll();
		return buildUsersDTO(listUsers);
	}// list

	@Override
	public List<UsersDTO> searchByTyping(Integer id, String username, String email, Boolean active) throws Exception {
		List<Users> listUsers = usersRepository.searchByTyping(id, username, email, active);

		return buildUsersDTO(listUsers);
	}// searchByTyping

	@Override
	public void update(UsersRequest req) throws Exception {
		Optional<Users> user = usersRepository.findById(req.getId());
		if (!user.isPresent())
			throw new Exception(serviceMessagesService.getMessage("user-noPresent"));

		// Verifica se l'username è già usato da un altro utente escludendo l'utente
		// attuale
		if (usersRepository.findByUsernameAndIdNot(req.getUsername(), req.getId()).isPresent()) {
			throw new Exception(serviceMessagesService.getMessage("user-Username"));
		}

		// Verifica se l'email è già usata da un altro utente escludendo l'utente
		// attuale
		if (usersRepository.findByEmailAndIdNot(req.getEmail(), req.getId()).isPresent()) {
			throw new Exception(serviceMessagesService.getMessage("user-email"));
		}

		Users users = user.get();
		users.setUsername(req.getUsername());
		users.setEmail(req.getEmail());

		// Controlla se la password nel request è diversa da quella salvata (decriptata)
		if (!passwordEncoder.matches(req.getPwd(), users.getPwd())) {
			String hashedPassword = passwordEncoder.encode(req.getPwd());
			users.setPwd(hashedPassword);
		}

		usersRepository.save(users);
	}// update

	/*
	 * solo admin può accedere a questo metodo
	 */
	@Override
	public void upgradeToAdmin(UsersRequest req) throws Exception {
		Optional<Users> user = usersRepository.findById(req.getId());
		if (!user.isPresent())
			throw new Exception(serviceMessagesService.getMessage("user-noPresent"));

		Optional<Roles> roleAdmin = rolesRepository.findByNameIgnoreCase("admin");
		if (!roleAdmin.isPresent())
			throw new Exception(serviceMessagesService.getMessage("role-noPresent"));

		Users users = user.get();
		users.setRole(roleAdmin.get());

		usersRepository.save(users);
	}// upgradeToAdmin

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(UsersRequest req) throws Exception {
		Optional<Users> users = usersRepository.findById(req.getId());
		if (!users.isPresent())
			throw new Exception(serviceMessagesService.getMessage("user-noPresent"));

		users.get().setActive(false);

		usersRepository.save(users.get());
	}// delete

}// class
