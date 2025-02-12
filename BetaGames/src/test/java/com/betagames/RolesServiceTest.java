package com.betagames;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.slf4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.betagames.controller.RolesController;
import com.betagames.dto.RolesDTO;
import com.betagames.request.RolesRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IRolesService;

/*
 * 
 * @author Simone Checco
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RolesServiceTest {
    @Autowired
    IRolesService rolesService;

    @Autowired
    RolesController rolesController;

    @Autowired
    Logger log;

    @Test
    @Order(1)
    public void createRolesTest() throws Exception {
        RolesRequest rolesReq = new RolesRequest();
        RolesRequest rolesReqTwo = new RolesRequest();
        rolesReq.setName("User");
        rolesReqTwo.setName("Client");

        ResponseBase createRolesOne = rolesController.create(rolesReq);

        ResponseBase createRolesTwo = rolesController.create(rolesReqTwo);

        Assertions.assertThat(createRolesOne.getRc()).isTrue();

        Assertions.assertThat(createRolesTwo.getRc()).isTrue();

        ResponseList<RolesDTO> res = rolesController.listRoles();

        Assertions.assertThat(res.getRc()).isTrue();

        Assertions.assertThat(res.getData().get(0).getName()).isEqualTo("User");

        Assertions.assertThat(res.getData().get(1).getName()).isEqualTo("Client");

    }

    @Test
    @Order(2)
    public void updateRolesTest() throws Exception {
        RolesRequest rolesRequest = new RolesRequest();
        rolesRequest.setId(1);
        rolesRequest.setName("Admin");

        ResponseBase updateRoles = rolesController.update(rolesRequest);

        Assertions.assertThat(updateRoles.getRc()).isTrue();

        ResponseList<RolesDTO> res = rolesController.listRoles();

        Assertions.assertThat(res.getRc()).isTrue();

        Assertions.assertThat(res.getData().get(0).getName()).isEqualTo("Admin");

    }

    @Test
    @Order(3)
    public void deleteRolesTest() throws Exception {
        RolesRequest rolesRequest = new RolesRequest();
        rolesRequest.setId(2);
        ResponseBase res = rolesController.delete(rolesRequest);

        Assertions.assertThat(res.getRc()).isTrue();

        ResponseList<RolesDTO> resList = rolesController.listRoles();

        Assertions.assertThat(resList.getRc()).isTrue();

        Assertions.assertThat(resList.getData().size()).isEqualTo(1);
    }

    @Test
    @Order(4)
    public void errorVoidRole() throws Exception {
        RolesRequest rolesRequest = new RolesRequest();
        rolesRequest.setName("");

        assertThrows(Exception.class, () -> {
            rolesService.create(rolesRequest);
        });
    }

    @Test
    @Order(5)
    public void errorRoleExists() throws Exception {
        RolesRequest rolesRequest = new RolesRequest();
        rolesRequest.setName("Client");

        assertThrows(Exception.class, () -> {
            rolesService.create(rolesRequest);
        });
    }

    @Test
    @Order(6)
    public void errorUpdate() throws Exception {
        RolesRequest rolesRequest = new RolesRequest();
        rolesRequest.setId(200);

        assertThrows(Exception.class, () -> {
            rolesService.update(rolesRequest);
        });
    }

    @Test
    @Order(7)
    public void errorDelete() throws Exception {
        RolesRequest rolesRequest = new RolesRequest();
        rolesRequest.setId(200);

        assertThrows(Exception.class, () -> {
            rolesService.delete(rolesRequest);
        });
    }
}
