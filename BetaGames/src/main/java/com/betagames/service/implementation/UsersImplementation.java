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

		Optional<Roles> role = rolesRepository.findByNameIgnoreCase("user");
		if(!role.isPresent())
			throw new Exception("Role not found");

		Optional<Users> users = usersRepository.findByUsername(req.getUsername());
		if (users.isPresent())
			throw new Exception("This username is already present");

		users = usersRepository.findByEmail(req.getEmail());
		if (users.isPresent())
			throw new Exception("This user email is already present");

		Users u = new Users();
		u.setUsername(req.getUsername());
		u.setEmail(req.getEmail());

		String hashedPassword = passwordEncoder.encode(req.getPwd());
		u.setPwd(hashedPassword);

		u.setActive(true);

		Carts cart = new Carts();
		cart.setUser(u);
		cart.setCreatedAt(now);
		cart.setUpdatedAt(now);
		cartsRepository.save(cart);

		u.setCart(cart);
		u.setRole(role.get());

		usersRepository.save(u);
	}// createUser

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createAdmin(UsersRequest req) throws Exception {


		Date now = new Date();
    
		Optional<Roles> role = rolesRepository.findByNameIgnoreCase("admin");
		if (!role.isPresent())
			throw new Exception("This role is not present");

		Optional<Users> users = usersRepository.findByUsername(req.getUsername());
		if (users.isPresent())
			throw new Exception("This username is already present");

		users = usersRepository.findByEmail(req.getEmail());
		if (users.isPresent())
			throw new Exception("This user email is already present");

		Users u = new Users();
		u.setUsername(req.getUsername());
		u.setEmail(req.getEmail());

		String hashedPassword = passwordEncoder.encode(req.getPwd());
		u.setPwd(hashedPassword);

		u.setActive(true);

		Carts cart = new Carts();
		cart.setUser(u);
		cart.setCreatedAt(now);
		cart.setUpdatedAt(now);
		cartsRepository.save(cart);

		u.setCart(cart);
		u.setRole(role.get());

		usersRepository.save(u);

	}// createAdmin

	/**
	 * DA MODIFICARE (posizione metodo, funzionalità max numero di inserimenti
	 * sbagliati)
	 */
	public void login(UsersRequest req) throws Exception {
		Optional<Users> users = usersRepository.findByUsername(req.getUsername());
		if (!users.isPresent())
			throw new Exception("Invalid password or username");

		if (!passwordEncoder.matches(req.getPwd(), users.get().getPwd()))
			throw new Exception("Invalid password or username");
	}// login

	@Override
	public void update(UsersRequest req) throws Exception {
		Optional<Users> optionalUser = usersRepository.findById(req.getId());
		if (!optionalUser.isPresent())
			throw new Exception("This user is not present");

		// Verifica se l'username è già usato da un altro utente escludendo l'utente corrente
		if (usersRepository.findByUsernameAndIdNot(req.getUsername(), req.getId()).isPresent()) {
			throw new Exception("This username is already in use by another user");
		}

		// Verifica se l'email è già usata da un altro utente escludendo l'utente corrente
		if (usersRepository.findByEmailAndIdNot(req.getEmail(), req.getId()).isPresent()) {
			throw new Exception("This email is already in use by another user");
		}

		Users user = optionalUser.get();
		user.setUsername(req.getUsername());
		user.setEmail(req.getEmail());

		// Controlla se la password nel request è diversa da quella salvata (decriptata)
		if (!passwordEncoder.matches(req.getPwd(), user.getPwd())) {
			String hashedPassword = passwordEncoder.encode(req.getPwd());
			user.setPwd(hashedPassword);
		}

		usersRepository.save(user);
	}// update

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
