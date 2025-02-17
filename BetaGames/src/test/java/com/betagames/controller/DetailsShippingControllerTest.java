package com.betagames.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.betagames.dto.DetailsShippingDTO;
import com.betagames.dto.ReviewsDTO;
import com.betagames.request.DetailsShippingRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

/**
 *
 * @author FabriniMarco
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DetailsShippingControllerTest {

	@Autowired
	Logger log;
	@Autowired
	DetailsShippingController detailsShippingController;
	@Autowired
	IRolesService rolesService;
	@Autowired
	IUsersService userService;

	private RolesRequest globalRolesAdminRequest;
	private RolesRequest globalRolesUserRequest;
	private UsersRequest globalUserRequest;
	private DetailsShippingRequest detailsShippingRequest;

	private void roles() throws Exception {
		globalRolesAdminRequest = new RolesRequest();
		globalRolesAdminRequest.setName("admin");
		rolesService.create(globalRolesAdminRequest);

		globalRolesUserRequest = new RolesRequest();
		globalRolesUserRequest.setName("user");
		rolesService.create(globalRolesUserRequest);
	}// roles

	private void user() throws Exception {
		roles();
		globalUserRequest = new UsersRequest();
		globalUserRequest.setUsername("userTest");
		globalUserRequest.setPwd("userTest");
		globalUserRequest.setEmail("userTest@example.com");
		userService.createUser(globalUserRequest);
	}// user

	private DetailsShippingRequest detailsShipping() throws Exception {
		user();
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setName("user");
		detailsShippingRequest.setLastname("lastname");
		detailsShippingRequest.setCountry("country");
		detailsShippingRequest.setStateRegion("stateRegion");
		detailsShippingRequest.setCap("00110");
		detailsShippingRequest.setCity("city");
		detailsShippingRequest.setAddress("address");
		detailsShippingRequest.setUserId(1);
		return detailsShippingRequest;
	}// detailsShipping

	@Test
	@Order(1)
	public void testCreateError() throws Exception {
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setId(1);
		detailsShippingRequest.setUserId(100);
		
		ResponseBase response = detailsShippingController.create(detailsShippingRequest);
		log.debug("testCreateError: {} " + response.getMsg());
	}// testCreateError
	
	@Test
	@Order(2)
	public void testCreate() throws Exception {
		ResponseBase response = detailsShippingController.create(detailsShipping());
		log.debug("testCreate: {} " + response.getMsg());
	}// testCreate

	@Test
	@Order(3)
	public void testList() throws Exception {
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setId(1);
		detailsShippingRequest.setUserId(1);
		
		ResponseList<DetailsShippingDTO> response = detailsShippingController.list(detailsShippingRequest);
		Assertions.assertThat(response.getRc()).isEqualTo(true);
		Assertions.assertThat(response.getData().get(0).getAddress()).isEqualTo("address");
		Assertions.assertThat(response.getData().get(0).getCap()).isEqualToIgnoringCase("00110");
		Assertions.assertThat(response.getData().get(0).getId()).isEqualTo(1);
	}// testList

	@Test
	@Order(4)
	public void testUpdateError() {
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setId(100);
		detailsShippingRequest.setUserId(1);
		
		ResponseBase response = detailsShippingController.update(detailsShippingRequest);
		log.debug("testUpdateError: {} " + response.getMsg());
	}// testUpdateError
	
	@Test
	@Order(5)
	public void testUpdate() {
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setId(1);
		detailsShippingRequest.setName("userUpdate");
		detailsShippingRequest.setLastname("lastname");
		detailsShippingRequest.setCountry("country");
		detailsShippingRequest.setStateRegion("stateRegionUpdate");
		detailsShippingRequest.setCap("00110");
		detailsShippingRequest.setCity("city");
		detailsShippingRequest.setAddress("address");
		detailsShippingRequest.setUserId(1);
		
		ResponseBase response = detailsShippingController.update(detailsShippingRequest);

		log.debug("testUpdate: {} " + response.getMsg());
	}// testUpdate

	@Test
	@Order(6)
	public void testDeleteError() {
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setId(100);
		detailsShippingRequest.setUserId(1);
		
		ResponseBase response = detailsShippingController.delete(detailsShippingRequest);
		log.debug("testDeleteError: {} " + response.getMsg());
	}// testDeleteError
	
	@Test
	@Order(7)
	public void testDelete() {
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setId(1);
		detailsShippingRequest.setUserId(1);
		
		ResponseBase response = detailsShippingController.delete(detailsShippingRequest);
		log.debug("testDelete: {} " + response.getMsg());
	}// testDelete

}// class