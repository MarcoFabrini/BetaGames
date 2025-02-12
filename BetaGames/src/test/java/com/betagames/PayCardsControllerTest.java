package com.betagames;

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

import com.betagames.controller.PayCardsController;
import com.betagames.dto.PayCardsDTO;
import com.betagames.dto.RolesDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.request.PayCardsRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PayCardsControllerTest {

    @Autowired
    PayCardsController payCardsController;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private IRolesService rolesService;

    @Autowired
    Logger log;

    /*
     * metodi del controller:
     *  create
     *  list
     *  listByUser
     *  update
     *  delete
    */

    @Test
    @Order(1)
    void createTest() throws Exception {

        RolesRequest rolesRequest = new RolesRequest();
        UsersRequest usersRequest = new UsersRequest();
        PayCardsRequest payCardsRequest = new PayCardsRequest();
        // ---------create Roles----------
        rolesRequest.setName("user");
        rolesService.create(rolesRequest);
        List<RolesDTO> listRoles = rolesService.listRoles();
        Assertions.assertThat(listRoles.size()).isEqualTo(1);
        // ---------Create User-------
        usersRequest.setUsername("userTest");
        usersRequest.setPwd("userTest");
        usersRequest.setEmail("userTest@example.com");
        usersRequest.setRoleId(1);
        usersService.createUser(usersRequest);
        List<UsersDTO> listUsers = usersService.searchByTyping(1, "userTest", "userTest@example.com", null);
        UsersDTO creaUsersDTO = listUsers.stream()
                .filter(e -> "userTest".equals(e.getUsername()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("User not found"));

        Assertions.assertThat(creaUsersDTO.getId()).isEqualTo(1);
        // ------create PayCard----------
        payCardsRequest.setBillingAddress("Via Dai Coiomberi, 1");
        payCardsRequest.setCardHolderName("Nome del tipo");
        payCardsRequest.setCardNumber("11223344");
        payCardsRequest.setCvv(133);
        payCardsRequest.setExpirationDate("31/12/2025");
        payCardsRequest.setUserId(listUsers.get(0).getId());

        ResponseBase response = payCardsController.create(payCardsRequest);

        //Asserts
        Assertions.assertThat(response.getRc()).isEqualTo(true);
    }//createTest

    @Test
    @Order(2)
    public void updateTest() throws Exception{
        PayCardsRequest payCardsRequest = new PayCardsRequest();
        //List<PayCardsDTO> listPayCards = payCardsService.list();
        List<UsersDTO> listUsers = usersService.list();
        payCardsRequest.setBillingAddress("Via Dei Martiri, 1");
        payCardsRequest.setCardHolderName("Simone Chindfo");
        payCardsRequest.setCardNumber("634562256");
        payCardsRequest.setCvv(234);
        payCardsRequest.setExpirationDate("31/12/2025");
        payCardsRequest.setId(1);
        payCardsRequest.setUserId(listUsers.get(0).getId());

        ResponseBase response = payCardsController.update(payCardsRequest);

        Assertions.assertThat(response.getRc()).isEqualTo(true);
    
    }//updateTest

    @Test
    @Order(5)
    public void deleteTest(){
        PayCardsRequest payCardsRequest = new PayCardsRequest();
        payCardsRequest.setId(1);

        ResponseBase response = payCardsController.delete(payCardsRequest);

        Assertions.assertThat(response.getRc()).isEqualTo(true);
    }


    @Test
    @Order(4)
    public void listTest(){
        ResponseList<PayCardsDTO> res = payCardsController.list();

        Assertions.assertThat(res.getRc()).isEqualTo(true);
        Assertions.assertThat(res.getData().get(0).getId()).isEqualTo(1);
    }//listTest

    @Test
    @Order(5)
    public void listByUserTest(){
        
        ResponseList<PayCardsDTO> res = payCardsController.listByUser(1);

        Assertions.assertThat(res.getRc()).isEqualTo(true);
        Assertions.assertThat(res.getData().size()).isEqualTo(1);
    }//listByUserIdTest

    @Test
    @Order(6)
    public void deleteFailTest(){
        PayCardsRequest payCardsRequest = new PayCardsRequest();
        payCardsRequest.setId(null);

        ResponseBase response = payCardsController.delete(payCardsRequest);

        Assertions.assertThat(response.getRc()).isEqualTo(false);
        Assertions.assertThat(response.getMsg()).isEqualTo("The given id must not be null");
    }

    //  @Test
    //  @Order(7)
    //  public void updateFailTest() throws Exception{
    //     PayCardsRequest payCardsRequest = new PayCardsRequest();
    //     List<UsersDTO> listUsers = usersService.list();
    //     payCardsRequest.setBillingAddress("Via Dei Martiri, 1");
    //     payCardsRequest.setCardHolderName("Simone Chindfo");
    //     payCardsRequest.setCardNumber("634562256");
    //     payCardsRequest.setCvv(234);
    //     payCardsRequest.setExpirationDate("31/12/2025");
    //     payCardsRequest.setId(1);
    //     payCardsRequest.setUserId(listUsers.get(0).getId());

    //     ResponseBase response = payCardsController.update(payCardsRequest);

    //     Assertions.assertThat(response.getRc()).isEqualTo(false);
    //     Assertions.assertThat(response.getMsg()).isEqualTo("Pay Card already present");

    //  }//updateFailTest



}//class
