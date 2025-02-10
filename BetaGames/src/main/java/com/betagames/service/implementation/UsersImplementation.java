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
import com.betagames.model.Orders;
import com.betagames.model.Reviews;
import com.betagames.model.Roles;
import com.betagames.model.Users;
import com.betagames.repository.ICartsRepository;
import com.betagames.repository.IOrdersRepository;
import com.betagames.repository.IReviewsRepository;
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
	private final IOrdersRepository ordersRepository;
	private final IRolesRepository rolesRepository;
	private final IReviewsRepository reviewsRepository;
	private final PasswordEncoder passwordEncoder;

	public UsersImplementation(Logger log, IUsersRepository usersRepository, ICartsRepository cartsRepository,
			IOrdersRepository ordersRepository, IRolesRepository rolesRepository, IReviewsRepository reviewsRepository,
			PasswordEncoder passwordEncoder) {
		this.log = log;
		this.usersRepository = usersRepository;
		this.cartsRepository = cartsRepository;
		this.ordersRepository = ordersRepository;
		this.rolesRepository = rolesRepository;
		this.reviewsRepository = reviewsRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<UsersDTO> list() throws Exception {
		List<Users> listUsers = usersRepository.findAll();

		return buildUsersDTO(listUsers);
	}// list

	@Override
	public List<UsersDTO> searchByTyping(Integer id, String username, String email) throws Exception {
		List<Users> listUsers = usersRepository.searchByTyping(id, username, email);

		return buildUsersDTO(listUsers);
	}// searchByTyping

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createUser(UsersRequest req) throws Exception {
		Date now = new Date();

		Optional<Roles> role = rolesRepository.findByName("user");

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
		Optional<Roles> role = rolesRepository.findByName("admin");

		Optional<Users> admin = usersRepository.findByUsername(req.getUsername());
		if (admin.isPresent())
			throw new Exception("This username is already present");

		admin = usersRepository.findByEmail(req.getEmail());
		if (admin.isPresent())
			throw new Exception("This user email is already present");

		Users a = new Users();
		a.setUsername(req.getUsername());
		a.setEmail(req.getEmail());

		String hashedPassword = passwordEncoder.encode(req.getPwd());
		a.setPwd(hashedPassword);
		a.setRole(role.get());

		usersRepository.save(a);
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

	/**
	 * sistemare update di pw
	 * fare mach della pw
	 * creare nuova pw e hash
	 * salvare
	 */
	@Override
	public void update(UsersRequest req) throws Exception {
		Optional<Users> users = usersRepository.findById(req.getId());
		if (!users.isPresent())
			throw new Exception("This user is not present");

		Integer userId = req.getId();

		// Verifica se l'username è già usato da un altro utente escludendo l'utente
		// corrente
		if (usersRepository.findByUsernameAndIdNot(req.getUsername(), userId).isPresent()) {
			throw new Exception("This username is already in use by another user");
		}

		// Verifica se l'email è già usata da un altro utente escludendo l'utente
		// corrente
		if (usersRepository.findByEmailAndIdNot(req.getEmail(), userId).isPresent()) {
			throw new Exception("This email is already in use by another user");
		}

		Users user = users.get();
		user.setUsername(req.getUsername());
		user.setEmail(req.getEmail());

		// implementare controllo se la pw cambia 
		String hashedPassword = passwordEncoder.encode(req.getPwd());
		user.setPwd(hashedPassword);

		usersRepository.save(user);
	}

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
