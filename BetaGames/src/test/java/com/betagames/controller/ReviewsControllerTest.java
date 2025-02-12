package com.betagames.controller;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.betagames.dto.ReviewsDTO;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.request.ReviewsRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IGamesService;
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
public class ReviewsControllerTest {

    @Autowired
    Logger log;
    @Autowired
    ReviewsController reviewsController;
    @Autowired
    IRolesService rolesService;
    @Autowired
    IUsersService userService;
    @Autowired
    IEditorsService editorsService;
    @Autowired
    IGamesService gamesService;

    private RolesRequest globalRolesAdminRequest;
    private RolesRequest globalRolesUserRequest;
    private UsersRequest globalUserRequest;
    private EditorsRequest globalEditorsRequest;
    private GamesRequest globalGamesRequest;
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

    private ReviewsRequest review() throws Exception {
        user();
        game();
        ReviewsRequest reviewsRequest = new ReviewsRequest();
        reviewsRequest.setScore(5);
        reviewsRequest.setDescription("description");
        reviewsRequest.setCreatedAt(now);
        reviewsRequest.setUsersId(1);
        reviewsRequest.setGameId(1);
        return reviewsRequest;
    }// review

    @Test
    @Order(1)
    void testCreate() throws Exception {
        ResponseBase response = reviewsController.create(review());
        log.debug("testCreate: {} " + response.getMsg());
    }// testCreate

    @Test
    @Order(2)
    void testListByUserId() {
        ResponseList<ReviewsDTO> response = reviewsController.listByUserId(1);

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        Assertions.assertThat(response.getData().get(0).getScore()).isEqualTo(5);
        Assertions.assertThat(response.getData().get(0).getDescription()).isEqualToIgnoringCase("description");
        Assertions.assertThat(response.getData().get(0).getId()).isEqualTo(1);
    }// testListByUserId

    @Test
    @Order(3)
    void testUpdateError() {
        ReviewsRequest reviewsRequest = new ReviewsRequest();
        reviewsRequest.setId(100);

        ResponseBase response = reviewsController.update(reviewsRequest);
        log.debug("testUpdateError: {} " + response.getMsg());
    }// testUpdateError

    @Test
    @Order(4)
    void testUpdate() {
        ReviewsRequest reviewsRequest = new ReviewsRequest();
        reviewsRequest.setId(1);
        reviewsRequest.setDescription("description updated");

        ResponseBase response = reviewsController.update(reviewsRequest);
        log.debug("testUpdate: {} " + response.getMsg());
    }// testUpdate

    @Test
    @Order(5)
    void testDeleteError() {
        ReviewsRequest reviewsRequest = new ReviewsRequest();
        reviewsRequest.setId(100);

        ResponseBase response = reviewsController.delete(reviewsRequest);
        log.debug("testDeleteError: {} " + response.getMsg());
    }// testDeleteError

    @Test
    @Order(6)
    void testDelete() {
        ReviewsRequest reviewsRequest = new ReviewsRequest();
        reviewsRequest.setId(1);

        ResponseBase response = reviewsController.delete(reviewsRequest);
        log.debug("testDelete: {} " + response.getMsg());
    }// testDelete
}// class
