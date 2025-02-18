package com.betagames.service.implementation;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.DetailsShippingDTO;
import com.betagames.model.DetailsShipping;
import com.betagames.model.Users;
import com.betagames.repository.IDetailsShippingsRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.DetailsShippingRequest;
import com.betagames.service.interfaces.IDetailsShippingService;
import com.betagames.service.interfaces.IServiceMessagesService;

import static com.betagames.utility.Utilities.buildDetailsShipping;

@Service
public class DetailsShippingImplementation implements IDetailsShippingService {

	@Autowired
	IServiceMessagesService serviceMessagesService;
	@Autowired
	Logger log;
	@Autowired
	IDetailsShippingsRepository detailsShippingsRepository;
	@Autowired
	IUsersRepository usersRepository;

	@Override
	public List<DetailsShippingDTO> list(DetailsShippingRequest req) throws Exception {
		Optional<Users> user = usersRepository.findById(req.getUserId());
		if (!user.isPresent())
			throw new Exception(serviceMessagesService.getMessage("user-noPresent"));

		return buildDetailsShipping(user.get().getListDetailsShippings());
	}// list

	@Override
	public void create(DetailsShippingRequest req) throws Exception {
		Optional<Users> user = usersRepository.findById(req.getUserId());
		if (!user.isPresent())
			throw new Exception(serviceMessagesService.getMessage("user-noPresent"));

		DetailsShipping ds = new DetailsShipping();
		ds.setName(req.getName());
		ds.setLastname(req.getLastname());
		ds.setCountry(req.getCountry());
		ds.setStateRegion(req.getStateRegion());
		ds.setCap(req.getCap());
		ds.setCity(req.getCity());
		ds.setAddress(req.getAddress());
		ds.setUser(user.get());
		ds.setActive(true);

		detailsShippingsRepository.save(ds);
	}// create

	@Override
	public void update(DetailsShippingRequest req) throws Exception {
		Optional<DetailsShipping> ds = detailsShippingsRepository.findById(req.getId());
		if (!ds.isPresent())
			throw new Exception(serviceMessagesService.getMessage("detailsShipping-noPresent"));

		ds.get().setName(req.getName());
		ds.get().setLastname(req.getLastname());
		ds.get().setCountry(req.getCountry());
		ds.get().setStateRegion(req.getStateRegion());
		ds.get().setCap(req.getCap());
		ds.get().setCity(req.getCity());
		ds.get().setAddress(req.getAddress());
		ds.get().setActive(req.getActive());

		detailsShippingsRepository.save(ds.get());
	}// update

	@Override
	public void delete(DetailsShippingRequest req) throws Exception {
		Optional<DetailsShipping> ds = detailsShippingsRepository.findById(req.getId());
		if (!ds.isPresent())
			throw new Exception(serviceMessagesService.getMessage("detailsShipping-noPresent"));

		ds.get().setActive(false);
		detailsShippingsRepository.save(ds.get());
	}// delete

}// class
