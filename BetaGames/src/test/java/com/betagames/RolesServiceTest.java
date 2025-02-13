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

import com.betagames.controller.RolesController;
import com.betagames.dto.RolesDTO;
import com.betagames.request.RolesRequest;
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
        rolesReq.setName("admin");
        rolesReqTwo.setName("user");

        rolesService.create(rolesReq);
        rolesService.create(rolesReqTwo);

        List<RolesDTO> res = rolesService.listRoles();

        Assertions.assertThat(res.get(0).getName()).isEqualTo("admin");

        Assertions.assertThat(res.get(1).getName()).isEqualTo("user");

    }

    @Test
    @Order(2)
    public void updateRolesTest() throws Exception {
        RolesRequest rolesRequest = new RolesRequest();
        rolesRequest.setId(1);
        rolesRequest.setName("superAdmin");

        rolesService.update(rolesRequest);

        List<RolesDTO> res = rolesService.listRoles();

        Assertions.assertThat(res.get(0).getName()).isEqualTo("superAdmin");

    }

    @Test
    @Order(3)
    public void deleteRolesTest() throws Exception {
        RolesRequest rolesRequest = new RolesRequest();
        rolesRequest.setId(2);
        rolesService.delete(rolesRequest);

        List<RolesDTO> resList = rolesService.listRoles();

        Assertions.assertThat(resList.size()).isEqualTo(1);
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
        List<RolesDTO> resList = rolesService.listRoles();
        rolesRequest.setName(resList.get(0).getName());

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
