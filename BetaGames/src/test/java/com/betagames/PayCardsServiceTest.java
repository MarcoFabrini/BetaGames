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

import com.betagames.dto.PayCardsDTO;
import com.betagames.dto.RolesDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.model.PayCards;
import com.betagames.request.OrdersRequest;
import com.betagames.request.PayCardsRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IPayCardsService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

/*
 * 
 * @author Simone Checco
 */

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PayCardsServiceTest {
    @Autowired
    private IPayCardsService payCardsService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private IRolesService rolesService;

    @Test
    @Order(1)
    public void createTest() throws Exception {

        RolesRequest rolesRequest = new RolesRequest();

        UsersRequest usersRequest = new UsersRequest();

        PayCardsRequest payCardsRequest = new PayCardsRequest();

        // ---------create Roles----------
        rolesRequest.setName("user");
        rolesService.create(rolesRequest);
        rolesRequest.setName("admin");
        rolesService.create(rolesRequest);


        List<RolesDTO> listRoles = rolesService.listRoles();

        Assertions.assertThat(listRoles.size()).isEqualTo(2);

        // ---------Create User-------
        usersRequest.setUsername("userTest");
        usersRequest.setPwd("userTest");
        usersRequest.setEmail("userTest@example.com");
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

        payCardsService.create(payCardsRequest);

        List<PayCardsDTO> listPayCard = payCardsService.list();
        listPayCard.forEach(card -> System.out.println("carta: " + card.getCardHolderName()));
        PayCardsDTO createPayCardDTO = listPayCard.stream()
                .filter(e -> "Nome del tipo".equals(e.getCardHolderName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("payCard not found"));

        Assertions.assertThat(createPayCardDTO.getId()).isEqualTo(1);

    }

    @Test
    @Order(2)
    public void listTest() throws Exception {
        List<PayCardsDTO> listPayCards = payCardsService.list();

        Assertions.assertThat(listPayCards.size()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void listByUserTest() throws Exception {

        List<PayCardsDTO> listPayCards = payCardsService.listByUser(1);

        Assertions.assertThat(listPayCards.size()).isEqualTo(1);
    }

    @Test
    @Order(4)
    public void updateTest() throws Exception {
        PayCardsRequest payCardsRequest = new PayCardsRequest();
        List<PayCardsDTO> listPayCards = payCardsService.list();
        List<UsersDTO> listUsers = usersService.list();
        payCardsRequest.setBillingAddress("Via Dei Martiri, 1");
        payCardsRequest.setCardHolderName("Simone Chindfo");
        payCardsRequest.setCardNumber("634562256");
        payCardsRequest.setCvv(234);
        payCardsRequest.setExpirationDate("31/12/2025");
        payCardsRequest.setId(1);
        payCardsRequest.setUserId(listUsers.get(0).getId());
        payCardsService.update(payCardsRequest);

        listPayCards = payCardsService.list();

        Assertions.assertThat(listPayCards.get(0).getCardNumber()).isEqualTo("634562256");
        Assertions.assertThat(listPayCards.get(0).getCvv()).isEqualTo(234);
        Assertions.assertThat(listPayCards.get(0).getCardHolderName()).isEqualTo("Simone Chindfo");
        Assertions.assertThat(listPayCards.get(0).getBillingAddress()).isEqualTo("Via Dei Martiri, 1");
    }

    @Test
    @Order(5)
    public void deleteTest() throws Exception {
        PayCardsRequest payCardsRequest = new PayCardsRequest();
        payCardsRequest.setId(1);

        payCardsService.delete(payCardsRequest);

        List<PayCardsDTO> listPayCards = payCardsService.list();

        Assertions.assertThat(listPayCards.get(0).getActive()).isEqualTo(false);
    }
}
