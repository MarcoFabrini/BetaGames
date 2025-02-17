package com.betagames.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

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
import com.betagames.request.DetailsShippingRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IDetailsShippingService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

/**
 *
 * @author FabriniMarco
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DetailsShippingServiceTest {
	@Autowired
	Logger log;
	@Autowired
	IDetailsShippingService detailsShippingService;
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

	private void detailsShipping() throws Exception {
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
		detailsShippingService.create(detailsShippingRequest);
	}// detailsShipping

	@Test
	@Order(1)
	public void createTest() throws Exception {
		detailsShipping();

		List<DetailsShippingDTO> list = detailsShippingService.list(detailsShippingRequest);

		DetailsShippingDTO foundDS = list.stream().filter(d -> "stateRegion".equals(d.getStateRegion())).findFirst()
				.orElseThrow(() -> new AssertionError("details shipping not found"));

		Assertions.assertThat(foundDS.getId()).isEqualTo(1);
		Assertions.assertThat(list.size()).isEqualTo(1);

		list.forEach(d -> log.debug("TEST create DetailsShipping: " + d.toString()));
	}// createTest

	@Test
	@Order(2)
	public void createNotUserIdTest() throws Exception {
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setUserId(100);

		Exception exception = assertThrows(Exception.class, () -> {
			detailsShippingService.create(detailsShippingRequest);
		});
		log.debug("createNotUserIdTest: {}", exception.getMessage());
	}// createNotUserIdTest

	@Test
	@Order(3)
	public void updateTest() throws Exception {
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setId(1);
		detailsShippingRequest.setName("user");
		detailsShippingRequest.setLastname("lastname");
		detailsShippingRequest.setCountry("country");
		detailsShippingRequest.setStateRegion("stateRegionUpdate");
		detailsShippingRequest.setCap("00110");
		detailsShippingRequest.setCity("city");
		detailsShippingRequest.setAddress("address");
		detailsShippingRequest.setUserId(1);

		detailsShippingRequest.setUserId(1);

		detailsShippingService.update(detailsShippingRequest);

		List<DetailsShippingDTO> list = detailsShippingService.list(detailsShippingRequest);

		DetailsShippingDTO foundDS = list.stream().filter(d -> "stateRegionUpdate".equals(d.getStateRegion()))
				.findFirst().orElseThrow(() -> new AssertionError("details shipping not found"));

		Assertions.assertThat(foundDS.getId()).isEqualTo(1);
		Assertions.assertThat(list.size()).isEqualTo(1);

		list.forEach(d -> log.debug("TEST create DetailsShipping: " + d.toString()));
	}// updateTest

	@Test
	@Order(4)
	public void updateNotDSIdTest() throws Exception {
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setId(100);

		Exception exception = assertThrows(Exception.class, () -> {
			detailsShippingService.update(detailsShippingRequest);
		});
		log.debug("updateNotDSIdTest: {}", exception.getMessage());

	}// updateNotDSIdTest

	@Test
	@Order(5)
	public void deleteNotDSIdTest() throws Exception {
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setId(100);
		detailsShippingRequest.setUserId(1);

		Exception exception = assertThrows(Exception.class, () -> {
			detailsShippingService.delete(detailsShippingRequest);
		});
		log.debug("deleteNotDSIdTest: {}", exception.getMessage());
	}// deleteNotDSIdTest

	@Test
	@Order(6)
	public void deleteTest() throws Exception {
		detailsShippingRequest = new DetailsShippingRequest();
		detailsShippingRequest.setId(1);
		detailsShippingRequest.setUserId(1);

		detailsShippingService.delete(detailsShippingRequest);
	}// deleteTest

}// class
