package com.betagames.service;

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

import com.betagames.dto.UsersDTO;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

/**
 *
 * @author FabriniMarco
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UsersServiceTest {
    @Autowired
    Logger log;
    @Autowired
    IRolesService rolesService;
    @Autowired
    IUsersService userService;

    private RolesRequest globalRolesAdminRequest;
    private RolesRequest globalRolesUserRequest;
    private UsersRequest globalAdminRequest;
    private UsersRequest globalUserRequest;

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
        globalAdminRequest = new UsersRequest();
        globalAdminRequest.setUsername("adminTest");
        globalAdminRequest.setPwd("adminTest");
        globalAdminRequest.setEmail("adminTest@example.com");
        userService.createUser(globalAdminRequest);

        globalUserRequest = new UsersRequest();
        globalUserRequest.setUsername("userTest");
        globalUserRequest.setPwd("userTest");
        globalUserRequest.setEmail("userTest@example.com");
        userService.createUser(globalUserRequest);
    }// user

    @Test
    @Order(1)
    public void createUsersTest() throws Exception {
        user();

        List<UsersDTO> listUsersDTO = userService.list();

        UsersDTO foundAdmin = listUsersDTO.stream()
                .filter(u -> "adminTest".equals(u.getUsername()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Review not found"));

        UsersDTO foundUsers = listUsersDTO.stream()
                .filter(u -> "userTest".equals(u.getUsername()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Review not found"));

        Assertions.assertThat(foundAdmin.getId()).isEqualTo(1);
        Assertions.assertThat(foundAdmin.getRole().getName()).isEqualToIgnoringCase("admin");
        Assertions.assertThat(foundUsers.getRole().getName()).isEqualToIgnoringCase("user");
        Assertions.assertThat(listUsersDTO.size()).isEqualTo(2);

        listUsersDTO.forEach(r -> log.debug("TEST create UsersTest: " + r.toString()));
    }// createUsersTest

    @Test
    @Order(2)
    public void createUsersDuplicateNameTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(2);
        usersRequest.setUsername("adminTest");

        Exception exception = assertThrows(Exception.class, () -> {
            userService.createUser(usersRequest);
        });
        log.debug("createUsersDuplicateNameTest: {}", exception.getMessage());
    }// createUsersDuplicateNameTest

    @Test
    @Order(3)
    public void createUsersDuplicateEmailTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(2);
        usersRequest.setEmail("adminTest@example.com");

        Exception exception = assertThrows(Exception.class, () -> {
            userService.createUser(usersRequest);
        });
        log.debug("createUsersDuplicateEmailTest: {}", exception.getMessage());
    }// createUsersDuplicateEmailTest

    @Test
    @Order(4)
    public void loginTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(1);
        usersRequest.setUsername("adminTest");
        usersRequest.setPwd("adminTest");

        userService.login(usersRequest);

        log.debug("loginTest: {SUCCESS LOGIN}");
    }// loginTest

    @Test
    @Order(5)
    public void loginWrongPwdTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(1);
        usersRequest.setUsername("adminTest");
        usersRequest.setPwd("adminTestERROR");

        Exception exception = assertThrows(Exception.class, () -> {
            userService.login(usersRequest);
        });
        log.debug("loginWrongPwdTest: {}", exception.getMessage());
    }// loginWrongPwdTest

    @Test
    @Order(6)
    public void loginWrongUsernameTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(1);
        usersRequest.setUsername("adminTestERROR");
        usersRequest.setPwd("adminTest");

        Exception exception = assertThrows(Exception.class, () -> {
            userService.login(usersRequest);
        });
        log.debug("loginWrongUsernameTest: {}", exception.getMessage());
    }// loginWrongUsernameTest

    @Test
    @Order(7)
    public void updateUsersTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(2);
        usersRequest.setUsername("userTestUpdate");
        usersRequest.setPwd("userTestUpdate");
        usersRequest.setEmail("userTestupdate@example.com");

        userService.update(usersRequest);

        List<UsersDTO> listUsersDTO = userService.list();

        UsersDTO foundUsers = listUsersDTO.stream()
                .filter(u -> "userTestUpdate".equals(u.getUsername()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Review not found"));

        Assertions.assertThat(foundUsers.getRole().getName()).isEqualToIgnoringCase("user");
        Assertions.assertThat(foundUsers.getEmail()).isEqualToIgnoringCase("userTestupdate@example.com");

        listUsersDTO.forEach(r -> log.debug("TEST update UsersTest: " + r.toString()));
    }// updateUsersTest

    @Test
    @Order(7)
    public void updateUsersNotIdTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(100);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.update(usersRequest);
        });
        log.debug("updateUsersNotIdTest: {}", exception.getMessage());
    }// updateUsersNotIdTest

    @Test
    @Order(8)
    public void updateDuplicateUsernameTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(2);
        usersRequest.setUsername("adminTest");
        usersRequest.setEmail("userTestupdate@example.com");

        Exception exception = assertThrows(Exception.class, () -> {
            userService.update(usersRequest);
        });
        log.debug("updateDuplicateUsernameTest: {}", exception.getMessage());
    }// updateDuplicateUsernameTest

    @Test
    @Order(9)
    public void updateDuplicateEmailTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(2);
        usersRequest.setUsername("userTestUpdate");
        usersRequest.setEmail("adminTest@example.com");

        Exception exception = assertThrows(Exception.class, () -> {
            userService.update(usersRequest);
        });
        log.debug("updateDuplicateEmailTest: {}", exception.getMessage());
    }// updateDuplicateEmailTest

    @Test
    @Order(10)
    public void updateNotPwdTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(2);
        usersRequest.setUsername("userTest");
        usersRequest.setPwd("userTestUpdate");
        usersRequest.setEmail("userTest@example.com");

        userService.update(usersRequest);

        List<UsersDTO> listUsersDTO = userService.searchByTyping(null, "userTest", null, true);

        listUsersDTO.forEach(r -> log.debug("updateNotPwdTest: {}" + r.toString()));
    }// updatePwdTest

    @Test
    @Order(11)
    public void upgradeToAdminTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(2);

        userService.upgradeToAdmin(usersRequest);

        List<UsersDTO> listUsersDTO = userService.searchByTyping(null, "userTest", null, true);

        listUsersDTO.forEach(r -> log.debug("upgradeToAdminTest: {}" + r.toString()));
    }// upgradeToAdminTest

    @Test
    @Order(12)
    public void upgradeToAdminNotUserIdTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(100);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.upgradeToAdmin(usersRequest);
        });
        log.debug("upgradeToAdminNotUserIdTest: {}", exception.getMessage());
    }// upgradeToAdminNotUserIdTest

    @Test
    @Order(13)
    public void deleteUsersNotIdTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(100);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.delete(usersRequest);
        });
        log.debug("deleteUsersNotIdTest: {}", exception.getMessage());
    }// deleteUsersNotIdTest

    @Test
    @Order(13)
    public void deleteUsersTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(2);

        userService.delete(usersRequest);

        List<UsersDTO> listUsersDTO = userService.list();
        
        Assertions.assertThat(listUsersDTO.size()).isEqualTo(2);

        listUsersDTO.forEach(r -> log.debug("deleteUsersTest: {}" + r.toString()));
    }// deleteUsersTest

}// class
