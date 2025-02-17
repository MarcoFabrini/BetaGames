package com.betagames.service;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.betagames.dto.ReviewsDTO;
import com.betagames.request.DetailsCartRequest;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.request.OrdersRequest;
import com.betagames.request.PayCardsRequest;
import com.betagames.request.ReviewsRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IDetailsCartsService;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IGamesService;
import com.betagames.service.interfaces.IOrdersService;
import com.betagames.service.interfaces.IPayCardsService;
import com.betagames.service.interfaces.IReviewsService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;
import static com.betagames.utility.Utilities.convertDateToString;

/**
 *
 * @author FabriniMarco
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ReviewsServiceTest {
	@Autowired
	Logger log;
	@Autowired
	IReviewsService reviewsService;
	@Autowired
	IRolesService rolesService;
	@Autowired
	IUsersService userService;
	@Autowired
	IEditorsService editorsService;
	@Autowired
	IGamesService gamesService;
	@Autowired
	IPayCardsService payCardsService;
	@Autowired
	IOrdersService ordersService;
	@Autowired
	IDetailsCartsService detailsCartsService;

	private RolesRequest globalRolesAdminRequest;
	private RolesRequest globalRolesUserRequest;
	private UsersRequest globalUserRequest;
	private EditorsRequest globalEditorsRequest;
	private GamesRequest globalGamesRequest;
	private PayCardsRequest payCardsRequest;
	private DetailsCartRequest detailsCartRequest;
	private OrdersRequest ordersRequest;
	private final Date now = new Date();

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

	private void payCard() throws Exception {
		payCardsRequest = new PayCardsRequest();
		payCardsRequest.setBillingAddress("Via Dai Coiomberi, 1");
		payCardsRequest.setCardHolderName("Nome del tipo");
		payCardsRequest.setCardNumber("11223344");
		payCardsRequest.setCvv(133);
		payCardsRequest.setExpirationDate("31/12/2025");
		payCardsRequest.setUserId(1);

		payCardsService.create(payCardsRequest);
	}// payCard

	private void editor() throws Exception {
		globalEditorsRequest = new EditorsRequest();
		globalEditorsRequest.setName("editorsTest");
		globalEditorsRequest.setWebsite("editorTest@example.com");
		editorsService.create(globalEditorsRequest);
	}// editor

	private void game() throws Exception {
		editor();
		globalGamesRequest = new GamesRequest();
		globalGamesRequest.setName("game test");
		globalGamesRequest.setDescription("description game");
		globalGamesRequest.setDate(convertDateToString(now));
		globalGamesRequest.setMaxGameTime(4);
		globalGamesRequest.setMinGameTime(2);
		globalGamesRequest.setMaxPlayerNumber(4);
		globalGamesRequest.setMinPlayerNumber(2);
		globalGamesRequest.setMinAge(10);
		globalGamesRequest.setPrice(50.00);
		globalGamesRequest.setStockQuantity(12);
		globalGamesRequest.setEditorsId(1);
		gamesService.create(globalGamesRequest);
	}// game

	private void dCart() throws Exception {
		detailsCartRequest = new DetailsCartRequest();
		detailsCartRequest.setCartId(1);
		detailsCartRequest.setGameId(1);
		detailsCartRequest.setQuantity(1);

		detailsCartsService.create(detailsCartRequest);
	}// dCart

	private void order() throws Exception {
		dCart();
		ordersRequest = new OrdersRequest();
		ordersRequest.setTotalAmount(52.68);
		ordersRequest.setCreatedAt("31/12/2025");
		ordersRequest.setUpdatedAt("31/12/2025");
		ordersRequest.setOrderStatus("ready");
		ordersRequest.setPayCardId(1);
		ordersRequest.setUserId(1);

		ordersService.create(ordersRequest);
	}// order

	private void review(Integer score, String description) throws Exception {
		user();
		payCard();
		game();
		order();
		ReviewsRequest reviewsRequest = new ReviewsRequest();
		reviewsRequest.setScore(score);
		reviewsRequest.setDescription(description);
		reviewsRequest.setCreatedAt(now);
		reviewsRequest.setUsersId(1);
		reviewsRequest.setGameId(1);
		reviewsService.create(reviewsRequest);
	}// review

	@Test
	@Order(1)
	public void createReviewsTest() throws Exception {
		review(5, "description");

		List<ReviewsDTO> listReviewsDTO = reviewsService.listByUserId(1);

		ReviewsDTO foundReview = listReviewsDTO.stream().filter(e -> "description".equals(e.getDescription()))
				.findFirst().orElseThrow(() -> new AssertionError("Review not found"));

		Assertions.assertThat(foundReview.getId()).isEqualTo(1);
		Assertions.assertThat(listReviewsDTO.size()).isEqualTo(1);

		listReviewsDTO.forEach(r -> log.debug("TEST create ReviewsTest: " + r.toString()));
	}// createReviewsTest

	@Test
	@Order(2)
	public void createReviewsNotUserIdTest() throws Exception {
		ReviewsRequest reviewsRequest = new ReviewsRequest();
		reviewsRequest.setId(1);
		reviewsRequest.setUsersId(100);
		reviewsRequest.setGameId(1);

		Exception exception = assertThrows(Exception.class, () -> {
			reviewsService.create(reviewsRequest);
		});
		log.debug("createReviewsNotUserIdTest: {}", exception.getMessage());
	}// createReviewsNotUserIdTest

	@Test
	@Order(3)
	public void createReviewsNotGameIdTest() throws Exception {
		ReviewsRequest reviewsRequest = new ReviewsRequest();
		reviewsRequest.setId(1);
		reviewsRequest.setUsersId(1);
		reviewsRequest.setGameId(100);

		Exception exception = assertThrows(Exception.class, () -> {
			reviewsService.create(reviewsRequest);
		});
		log.debug("createReviewsNotGameIdTest: {}", exception.getMessage());
	}// createReviewsNotGameIdTest

	@Test
	@Order(4)
	public void updateReviewsTest() throws Exception {
		ReviewsRequest reviewsRequest = new ReviewsRequest();
		reviewsRequest.setId(1);
		reviewsRequest.setScore(0);
		reviewsRequest.setDescription("description updated");
		reviewsRequest.setCreatedAt(now);
		reviewsRequest.setUsersId(1);
		reviewsRequest.setGameId(1);

		reviewsService.update(reviewsRequest);

		List<ReviewsDTO> listReviewsDTO = reviewsService.listByUserId(1);

		ReviewsDTO foundReview = listReviewsDTO.stream().filter(e -> "description updated".equals(e.getDescription()))
				.findFirst().orElseThrow(() -> new AssertionError("Review not found"));

		Assertions.assertThat(foundReview.getId()).isEqualTo(1);
		Assertions.assertThat(listReviewsDTO.size()).isEqualTo(1);

		listReviewsDTO.forEach(r -> log.debug("TEST update ReviewsTest: " + r.toString()));
	}// updateReviewsTest

	@Test
	@Order(5)
	public void updateReviewsNotIdTest() throws Exception {
		ReviewsRequest reviewsRequest = new ReviewsRequest();
		reviewsRequest.setId(100);

		Exception exception = assertThrows(Exception.class, () -> {
			reviewsService.update(reviewsRequest);
		});
		log.debug("updateReviewsNotIdTest: {}", exception.getMessage());
	}// updateReviewsNotIdTest

	@Test
	@Order(6)
	public void updateReviewsNotUserIdTest() throws Exception {
		ReviewsRequest reviewsRequest = new ReviewsRequest();
		reviewsRequest.setId(1);
		reviewsRequest.setUsersId(100);
		reviewsRequest.setGameId(1);

		Exception exception = assertThrows(Exception.class, () -> {
			reviewsService.update(reviewsRequest);
		});
		log.debug("updateReviewsNotUserIdTest: {}", exception.getMessage());
	}// updateReviewsNotIdTest

	@Test
	@Order(7)
	public void updateReviewsNotGameIdTest() throws Exception {
		ReviewsRequest reviewsRequest = new ReviewsRequest();
		reviewsRequest.setId(1);
		reviewsRequest.setUsersId(1);
		reviewsRequest.setGameId(100);

		Exception exception = assertThrows(Exception.class, () -> {
			reviewsService.update(reviewsRequest);
		});
		log.debug("updateReviewsNotGameIdTest: {}", exception.getMessage());
	}// updateReviewsNotIdTest

	@Test
	@Order(8)
	public void deleteReviewsNotIdTest() throws Exception {
		ReviewsRequest reviewsRequest = new ReviewsRequest();
		reviewsRequest.setId(100);

		Exception exception = assertThrows(Exception.class, () -> {
			reviewsService.delete(reviewsRequest);
		});
		log.debug("deleteReviewsNotIdTest: {}", exception.getMessage());
	}// deleteReviewsNotIdTest

	@Test
	@Order(9)
	public void deleteReviewsTest() throws Exception {
		ReviewsRequest reviewsRequest = new ReviewsRequest();
		reviewsRequest.setId(1);

		reviewsService.delete(reviewsRequest);

		List<ReviewsDTO> listReviewsDTO = reviewsService.listByUserId(1);

		Assertions.assertThat(listReviewsDTO.size()).isEqualTo(0);

		listReviewsDTO.forEach(r -> log.debug("TEST delete ReviewsTest: " + r.toString()));
	}// deleteReviewsTest

}// class
