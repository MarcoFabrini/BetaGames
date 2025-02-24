package com.betagames.service.implementation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.SignInDTO;
import com.betagames.dto.TokenDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.model.Carts;
import com.betagames.model.Roles;
import com.betagames.model.Users;
import com.betagames.repository.ICartsRepository;
import com.betagames.repository.IRolesRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.SignInRequest;
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
	@Autowired
	ICartsRepository cartsRepository;

	private final IUsersRepository usersRepository;
	private final IRolesRepository rolesRepository;
	private final PasswordEncoder passwordEncoder;

	public UsersImplementation(IUsersRepository usersRepository,
			IRolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
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

	@Override
	public SignInDTO signIn(SignInRequest req) {
		SignInDTO resp = new SignInDTO();
		Optional<Users> uA = usersRepository.findByUsernameAndPwd(req.getUserName().trim(), req.getPwd().trim());
		if (uA.isEmpty())
			resp.setLogged(false);
		else {
			resp.setId(uA.get().getId());
			resp.setLogged(true);
			resp.setRole(uA.get().getRole().toString());
		}
		
		return resp;
	}//signIn

	@Override
    public SignInDTO login(UsersRequest req) throws Exception {
		// Cerca l'utente nel repository in base al nome utente fornito
		Optional<Users> users = usersRepository.findByUsername(req.getUsername());
		
		// Verifica se l'utente esiste e se la password fornita corrisponde a quella
		// memorizzata
		if (!users.isPresent() || !passwordEncoder.matches(req.getPwd(), users.get().getPwd())) {
			throw new Exception("Invalid password or username");
		}

	
		SignInDTO resp = new SignInDTO();
		if (users.isEmpty())
		resp.setLogged(false);
		else {
			resp.setId(users.get().getId());
			resp.setLogged(true);
			resp.setRole(users.get().getRole().getName());
		}
		
		return resp;
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

	@Override
	public void changePWD(SignInRequest req) throws Exception {
		
		Optional<Users> uA = usersRepository.findById(req.getId());
		if (uA.isEmpty())
			throw new Exception("utente non trovato");
		if (!passwordEncoder.matches(req.getPwd(), uA.get().getPwd()))
			throw new Exception("password invalido");
		
		String encode = passwordEncoder.encode(req.getNewPwd());
		uA.get().setPwd(encode);
		
		usersRepository.save(uA.get());
	}//changePWD

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createUser(UsersRequest req) throws Exception {
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
	}// createUser

}// class
