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

import com.betagames.dto.UsersDTO;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IRolesService;

/**
 *
 * @author FabriniMarco
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UsersControllerTest {

    @Autowired
    Logger log;
    @Autowired
    UsersController usersController;
    @Autowired
    IRolesService rolesService;

    private RolesRequest globalRolesAdminRequest;
    private RolesRequest globalRolesUserRequest;
    private UsersRequest globalAdminRequest;

    private void roles() throws Exception {
        globalRolesAdminRequest = new RolesRequest();
        globalRolesAdminRequest.setName("admin");
        rolesService.create(globalRolesAdminRequest);

        globalRolesUserRequest = new RolesRequest();
        globalRolesUserRequest.setName("user");
        rolesService.create(globalRolesUserRequest);
    }// roles

    private UsersRequest user() throws Exception {
        globalAdminRequest = new UsersRequest();
        globalAdminRequest.setUsername("adminTest");
        globalAdminRequest.setPwd("adminTest");
        globalAdminRequest.setEmail("adminTest@example.com");

        return globalAdminRequest;
    }// user

    @Test
    @Order(1)
    void testCreateUser() throws Exception {
        roles();
        UsersRequest adminRequest = user();
        UsersRequest userRequest = user();

        ResponseBase response = usersController.createUser(adminRequest);
        ResponseBase response2 = usersController.createUser(userRequest);

        Assertions.assertThat(adminRequest.getUsername()).isEqualTo("adminTest");

        log.debug("testCreateUser: {} " + response.getMsg());
        log.debug("testCreateUserError: {} " + response2.getMsg());
    }// testCreateUser

    @Test
    @Order(2)
    void testSearchByTyping() {
        ResponseList<UsersDTO> response = usersController.searchByTyping(1, null, null, null);

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        Assertions.assertThat(response.getData().get(0).getUsername()).isEqualTo("adminTest");
        Assertions.assertThat(response.getData().get(0).getEmail()).isEqualToIgnoringCase("adminTest@example.com");
        Assertions.assertThat(response.getData().get(0).getActive()).isEqualTo(true);
        Assertions.assertThat(response.getData().get(0).getId()).isEqualTo(1);
    }// testSearchByTyping

    @Test
    @Order(3)
    void testList() {
        ResponseList<UsersDTO> response = usersController.list();

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        Assertions.assertThat(response.getData().get(0).getUsername()).isEqualTo("adminTest");
        Assertions.assertThat(response.getData().get(0).getEmail()).isEqualToIgnoringCase("adminTest@example.com");
        Assertions.assertThat(response.getData().get(0).getActive()).isEqualTo(true);
        Assertions.assertThat(response.getData().get(0).getId()).isEqualTo(1);
    }// testList

    @Test
    @Order(4)
    void testLogin() {
        UsersRequest req = new UsersRequest();
        req.setUsername("adminTest");
        req.setPwd("adminTest");

        ResponseBase response = usersController.login(req);
        log.debug("testLogin: {} " + response.getMsg());
    }// testLogin

    @Test
    @Order(5)
    void testLoginWrongUsername() {
        UsersRequest req = new UsersRequest();
        req.setUsername("adminTestError");
        req.setPwd("adminTest");

        ResponseBase response = usersController.login(req);
        log.debug("testLoginWrongUsername: {} " + response.getMsg());
    }// testLoginWrongUsername

    @Test
    @Order(6)
    void testLoginWrongPwd() {
        UsersRequest req = new UsersRequest();
        req.setUsername("adminTest");
        req.setPwd("adminTestError");

        ResponseBase response = usersController.login(req);
        log.debug("testLoginWrongPwd: {} " + response.getMsg());
    }// testLoginWrongPwd

    @Test
    @Order(7)
    void testUpdate() {
        UsersRequest req = new UsersRequest();
        req.setId(1);
        req.setUsername("adminTestUpdate");
        req.setPwd("adminTest");
        req.setEmail("adminTest@example.com");

        ResponseBase response = usersController.update(req);

        log.debug("testUpdate: {} " + response.getMsg());
    }// testUpdate

    @Test
    @Order(8)
    void testUpdateWrongUserId() {
        UsersRequest req = new UsersRequest();
        req.setId(100);
        req.setUsername("adminTest");
        req.setPwd("adminTestUpdate");
        req.setEmail("adminTest@example.com");

        ResponseBase response = usersController.update(req);

        log.debug("testUpdateWrongUserId: {} " + response.getMsg());
    }// testUpdateWrongUserId

    @Test
    @Order(9)
    void testUpgradeToAdmin() {
        UsersRequest user = new UsersRequest();
        user.setId(2);
        user.setUsername("userTest");
        user.setPwd("userTest");
        user.setEmail("userTest@example.com");
        usersController.createUser(user);


        ResponseBase response = usersController.upgradeToAdmin(user);
        log.debug("testUpgradeToAdmin: {} " + response.getMsg());
    }// testUpgradeToAdmin

    @Test
    @Order(10)
    void testUpgradeToAdminWrongUserId() {
        UsersRequest user = new UsersRequest();
        user.setUsername("userTest2");
        user.setPwd("userTest");
        user.setEmail("userTest2@example.com");
        usersController.createUser(user);

        UsersRequest user2 = new UsersRequest();
        user2.setId(100);

        ResponseBase response = usersController.upgradeToAdmin(user2);
        log.debug("testUpgradeToAdmin: {} " + response.getMsg());
    }// testUpgradeToAdminWrongUserId

    @Test
    @Order(11)
    void testDeleteWrongUserId() {
        UsersRequest user = new UsersRequest();
        user.setId(100);

        ResponseBase response = usersController.delete(user);
        log.debug("testDeleteWrongUserId: {} " + response.getMsg());
    }// testDeleteWrongUserId

    @Test
    @Order(12)
    void testDelete() {
        UsersRequest user = new UsersRequest();
        user.setId(1);
        UsersRequest user2 = new UsersRequest();
        user2.setId(2);

        ResponseBase response = usersController.delete(user);
        ResponseBase response2 = usersController.delete(user2);
        log.debug("testDeleteWrongUserId: {} " + response.getMsg());
        log.debug("testDeleteWrongUserId: {} " + response2.getMsg());
    }// testDelete

}// class
