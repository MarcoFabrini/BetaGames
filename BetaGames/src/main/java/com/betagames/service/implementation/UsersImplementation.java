package com.betagames.service.implementation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.UsersDTO;
import com.betagames.model.Carts;
import com.betagames.model.Roles;
import com.betagames.model.Users;
import com.betagames.repository.ICartsRepository;
import com.betagames.repository.IRolesRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IUsersService;
import static com.betagames.utility.Utilities.buildUsersDTO;

/**
 *
 * @author FabriniMarco
 */
@Service
public class UsersImplementation implements IUsersService {
	private final Logger log;
	private final IUsersRepository usersRepository;
	private final ICartsRepository cartsRepository;
	private final IRolesRepository rolesRepository;
	private final PasswordEncoder passwordEncoder;

	public UsersImplementation(Logger log, IUsersRepository usersRepository, ICartsRepository cartsRepository,
			IRolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
		this.log = log;
		this.usersRepository = usersRepository;
		this.cartsRepository = cartsRepository;
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
	@Transactional(rollbackFor = Exception.class)
	public void createUser(UsersRequest req) throws Exception {
		Date now = new Date();

		Optional<Roles> roleUser = rolesRepository.findByNameIgnoreCase("user");
		Optional<Roles> roleAdmin = rolesRepository.findByNameIgnoreCase("admin");

		if (!roleUser.isPresent() || !roleAdmin.isPresent())
			throw new Exception("Role not found");

		// Verifica se è il primo utente nel sistema
		boolean isFirstUser = !usersRepository.findTopBy().isPresent();

		if (usersRepository.findByUsername(req.getUsername()).isPresent())
			throw new Exception("This username is already present");

		if (usersRepository.findByEmail(req.getEmail()).isPresent())
			throw new Exception("This user email is already present");

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
	}// createUser

	@Override
	public void update(UsersRequest req) throws Exception {
		Optional<Users> user = usersRepository.findById(req.getId());
		if (!user.isPresent())
			throw new Exception("This user is not present");

		// Verifica se l'username è già usato da un altro utente escludendo l'utente
		// attuale
		if (usersRepository.findByUsernameAndIdNot(req.getUsername(), req.getId()).isPresent()) {
			throw new Exception("This username is already in use by another user");
		}

		// Verifica se l'email è già usata da un altro utente escludendo l'utente
		// attuale
		if (usersRepository.findByEmailAndIdNot(req.getEmail(), req.getId()).isPresent()) {
			throw new Exception("This email is already in use by another user");
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
			throw new Exception("This user is not present");

		Optional<Roles> roleAdmin = rolesRepository.findByNameIgnoreCase("admin");
		if (!roleAdmin.isPresent())
			throw new Exception("Roles not found");

		Users users = user.get();
		users.setRole(roleAdmin.get());

		usersRepository.save(users);
	}// upgradeToAdmin

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(UsersRequest req) throws Exception {
		Optional<Users> users = usersRepository.findById(req.getId());
		if (!users.isPresent())
			throw new Exception("This user is not present");

		users.get().setActive(false);

		usersRepository.save(users.get());
	}// delete

}// class
