package com.betagames.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.UsersDTO;
import com.betagames.model.Users;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IUsersService;
import static com.betagames.utility.Utilities.buildUsersDTO;

/**
 *
 * @author Fabrini Marco
 */
@Service
public class UsersImplementation implements IUsersService {
	@Autowired
	Logger log;
	@Autowired
	IUsersRepository usersRepository;

	@Override
	public List<UsersDTO> list() throws Exception {
		List<Users> listUsers = usersRepository.findAll();
		return buildUsersDTO(listUsers);
	}// list

	@Override
	public List<UsersDTO> searchByTyping(Integer id, String username, String email) throws Exception {

		List<Users> listUsers = usersRepository.searchByTyping(id, username, email);

		return listUsers.stream()
				.map(u -> new UsersDTO(u.getId(), u.getUsername(), u.getEmail(), null, null, null, null, null))
				.collect(Collectors.toList());
	}// searchByTyping

	@Override
	public void create(UsersRequest req) throws Exception {
		Optional<Users> users = usersRepository.findByUsername(req.getUsername());
		if (users.isPresent())
			throw new Exception("This username is already present");

		users = usersRepository.findByEmail(req.getEmail());
		if (users.isPresent())
			throw new Exception("This user email is already present");

		Users u = new Users();
		u.setUsername(req.getUsername());
		u.setEmail(req.getEmail());
		
		// criptare la pw
		u.setPwd(req.getPwd());

		usersRepository.save(u);
	}// create

	@Override
	public void update(UsersRequest req) throws Exception {
		Optional<Users> users = usersRepository.findById(req.getId());
		if (!users.isPresent())
			throw new Exception("This user is not present");

		Optional<Users> u = usersRepository.findByUsername(req.getUsername());
		if (u.isPresent())
			throw new Exception("This username is already present");

		u = usersRepository.findByEmail(req.getEmail());
		if (u.isPresent())
			throw new Exception("This user email is already present");

		users.get().setUsername(req.getUsername());
		users.get().setEmail(req.getEmail());

		// criptare la pw
		users.get().setPwd(req.getPwd());

		usersRepository.save(users.get());
	}// update

	@Override
	public void delete(UsersRequest req) throws Exception {
		Optional<Users> users = usersRepository.findById(req.getId());
		if (!users.isPresent())
			throw new Exception("This user is not present");

		usersRepository.delete(users.get());
	}// delete

}// class
