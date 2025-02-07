package com.betagames;

import java.util.List;

import org.slf4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.betagames.dto.RolesDTO;
import com.betagames.request.RolesRequest;
import com.betagames.service.interfaces.IRolesService;

/*
 * 
 * @author Simone Checco
 */
@SpringBootTest
@ActiveProfiles(value="test")
public class RolesServiceTest {
    @Autowired
    IRolesService rolesService;

    @Autowired
    Logger log;

    @Test
    @Order(1)
    public void createRolesTest() throws Exception{
        RolesRequest rolesReq = new RolesRequest();
        RolesRequest rolesReqTwo = new RolesRequest();
        rolesReq.setName("User");
        rolesReqTwo.setName("Client");
        rolesService.create(rolesReq);
        rolesService.create(rolesReqTwo);

        List<RolesDTO> listRoles = rolesService.listRoles();

        RolesDTO verificaRolesDTO = listRoles.stream()
                .filter(e -> "User".equals(e.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Roles not found"));
        
        Assertions.assertThat(verificaRolesDTO.getId()).isEqualTo(1);

        listRoles.forEach(e -> log.debug("Test: " + e.toString()));
    }

    @Test
    @Order(2)
    public void updateRolesTest() throws Exception{
        RolesRequest rolesRequest = new RolesRequest();
        rolesRequest.setId(1);
        rolesRequest.setName("Admin");

        rolesService.update(rolesRequest);

        List<RolesDTO> listRoles = rolesService.listRoles();

        RolesDTO verificaRolesDTO = listRoles.stream()
                .filter(e -> "Admin".equals(e.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Roles not found"));
        
        Assertions.assertThat(verificaRolesDTO.getName()).isEqualTo("Admin");
    }
    @Test
    @Order(3)
    public void deleteRolesTest() throws Exception{
        RolesRequest rolesRequest = new RolesRequest();

        rolesRequest.setId(2);
        rolesService.delete(rolesRequest);

        List<RolesDTO> listRoles = rolesService.listRoles();

        Assertions.assertThat(listRoles.size()).isEqualTo(1);
    }
    @Test
    @Order(4)
    public void listRolesTest() throws Exception{
        List<RolesDTO> listRoles = rolesService.listRoles();

        Assertions.assertThat(listRoles.size()).isEqualTo(2);
    }
}
