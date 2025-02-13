package com.betagames;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.betagames.dto.CartsDTO;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.ICartsService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CartsServiceTest {
    
    @Autowired
    IRolesService rolesService;
    @Autowired
    IUsersService userService;
    @Autowired
    ICartsService cartsService;

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

    @Order(1)
    @Test
    public void listCartsTest() throws Exception {
        user();

        List<CartsDTO> listCart = cartsService.list();

        Assertions.assertThat((listCart.size())).isEqualTo(2);
    }
}
