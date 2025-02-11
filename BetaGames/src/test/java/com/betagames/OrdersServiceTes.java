package com.betagames;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
import org.springframework.test.context.ActiveProfiles;

import com.betagames.dto.AuthorsDTO;
import com.betagames.dto.DetailsCartDTO;
import com.betagames.dto.DetailsOrderDTO;
import com.betagames.dto.EditorsDTO;
import com.betagames.dto.GamesDTO;
import com.betagames.dto.OrdersDTO;
import com.betagames.dto.PayCardsDTO;
import com.betagames.dto.RolesDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.model.PayCards;
import com.betagames.repository.IDetailsCartsRepository;
import com.betagames.request.AuthorsRequest;
import com.betagames.request.DetailsCartRequest;
import com.betagames.request.DetailsOrderRequest;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.request.OrdersRequest;
import com.betagames.request.PayCardsRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IAuthorsService;
import com.betagames.service.interfaces.IDetailsCartsService;
import com.betagames.service.interfaces.IDetailsOrderService;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IGamesService;
import com.betagames.service.interfaces.IOrdersService;
import com.betagames.service.interfaces.IPayCardsService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

/*
 * 
 * @author Simone Checco
 */

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdersServiceTes {

    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private IDetailsOrderService detailsOrderService;
    @Autowired
    private IPayCardsService payCardsService;
    @Autowired
    private IUsersService usersService;
    @Autowired
    private IRolesService rolesService;
    @Autowired
    private IGamesService gamesService;
    @Autowired
    private IEditorsService editorsService;
    @Autowired
    private IDetailsCartsService detailsCartsService;

    @Autowired
    Logger log;

    @Test
    @Order(1)
    public void createTest() throws Exception {

        // -----Request------
        DetailsOrderRequest detailsOrderRequest = new DetailsOrderRequest();

        OrdersRequest orderRequest = new OrdersRequest();

        PayCardsRequest payCardsRequest = new PayCardsRequest();

        UsersRequest usersRequest = new UsersRequest();

        RolesRequest rolesRequest = new RolesRequest();

        EditorsRequest editorsRequest = new EditorsRequest();

        GamesRequest gamesRequest = new GamesRequest();

        DetailsCartRequest detailsCartRequest = new DetailsCartRequest();

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

        List<UsersDTO> listUsers = usersService.searchByTyping(1, "userTest", "userTest@example.com");

        UsersDTO creaUsersDTO = listUsers.stream()
                .filter(e -> "userTest".equals(e.getUsername()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("User not found"));

        Assertions.assertThat(creaUsersDTO.getId()).isEqualTo(1);

        // ------create PayCard----------
        payCardsRequest.setBillingAddress("Via Dai Coiomberi, 1");
        payCardsRequest.setCardHolderName("Nome del tipo");
        payCardsRequest.setCardNumber(11223344);
        payCardsRequest.setCvv(133);
        payCardsRequest.setExpirationDate("31/12/2025");
        payCardsRequest.setUserId(1);

        payCardsService.create(payCardsRequest);

        List<PayCardsDTO> listPayCard = payCardsService.list();
        listPayCard.forEach(card -> System.out.println("carta: " + card.getCardHolderName()));
        PayCardsDTO createPayCardDTO = listPayCard.stream()
                .filter(e -> "Nome del tipo".equals(e.getCardHolderName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("payCard not found"));

        Assertions.assertThat(createPayCardDTO.getId()).isEqualTo(1);

        // --------Create Editors--------
        editorsRequest.setName("editorsName");
        editorsRequest.setWebsite("website");

        editorsService.create(editorsRequest);

        List<EditorsDTO> listEditors = editorsService.list();

        EditorsDTO creaEditorsDTO = listEditors.stream()
                .filter(e -> "editorsName".equals(e.getName()))
                .findFirst()
                // .filter(e -> "website".equals(e.getEmail()))
                .orElseThrow(() -> new AssertionError("Editor not found"));

        Assertions.assertThat(creaEditorsDTO.getId()).isEqualTo(1);

        // -------Create Games--------
        gamesRequest.setName("Giochino");
        gamesRequest.setDescription("Descrizione del giochino");
        gamesRequest.setDate("01/01/2025");
        gamesRequest.setMaxGameTime(4);
        gamesRequest.setMinGameTime(2);
        gamesRequest.setMaxPlayerNumber(4);
        gamesRequest.setMinPlayerNumber(2);
        gamesRequest.setMinAge(10);
        gamesRequest.setPrice(50.00);
        gamesRequest.setStockQuantity(12);
        gamesRequest.setEditorsId(1);

        gamesService.create(gamesRequest);
        List<GamesDTO> lstGames = gamesService.list();

        Assertions.assertThat((lstGames.size())).isEqualTo(1);

        // -----------Create DetailsCart---------
        detailsCartRequest.setCartId(creaUsersDTO.getCarts().getId());
        detailsCartRequest.setGameId(lstGames.get(0).getId());
        detailsCartRequest.setPriceAtTime(12.56);
        detailsCartRequest.setQuantity(2);

        detailsCartsService.create(detailsCartRequest);

        List<DetailsCartDTO> listDetailsCart = detailsCartsService.list();

        Assertions.assertThat((listDetailsCart.size())).isEqualTo(1);

        // -----------Create Order---------
        orderRequest.setTotalAmount(52.68);
        orderRequest.setCreatedAt("31/12/2025");
        orderRequest.setUpdatedAt("31/12/2025");
        orderRequest.setOrderStatus("ready");
        orderRequest.setPayCardId(1);
        orderRequest.setUserId(1);

        ordersService.create(orderRequest);
        List<OrdersDTO> listOrder = ordersService.findAllOrders();

        Assertions.assertThat((listOrder.size())).isEqualTo(1);

        // -------Create DetailsOrder--------
        detailsOrderRequest.setPriceAtTime(12.56);
        detailsOrderRequest.setQuantity(4);
        detailsOrderRequest.setOrdersId(1);
        detailsOrderRequest.setGameId(1);

        detailsOrderService.create(detailsOrderRequest);

        List<DetailsOrderDTO> listDetailOrder = detailsOrderService.searchByOrder(1);

        Assertions.assertThat((listDetailOrder.size())).isEqualTo(1);

    }

    // --------order update------
    @Test
    @Order(2)
    public void updateTest() throws Exception {

        OrdersRequest orderRequest = new OrdersRequest();
        orderRequest.setId(1);
        orderRequest.setTotalAmount(25.12);
        orderRequest.setCreatedAt("06/02/2025");
        orderRequest.setUpdatedAt("07/02/2025");
        orderRequest.setOrderStatus("shipped");
        orderRequest.setPayCardId(1);

        orderRequest.setUserId(1);

        ordersService.update(orderRequest);

        List<OrdersDTO> listOrders = ordersService.findAllOrders();

        OrdersDTO updateOrdersDTO = listOrders.stream()
                .filter(e -> "shipped".equals(e.getOrderStatus()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Orders not found"));
        Assertions.assertThat(updateOrdersDTO.getId()).isEqualTo(1);

    }

    // ---orderList----

    @Test
    @Order(3)
    public void readAllTest() throws Exception {
        List<OrdersDTO> listOrders = ordersService.findAllOrders();

        Assertions.assertThat(listOrders.size()).isEqualTo(1);
    }

    @Test
    @Order(4)
    public void readByOrderId() throws Exception {
        List<OrdersDTO> listOrders = ordersService.findByUser(1);

        Assertions.assertThat(listOrders.size()).isEqualTo(1);
    }

}
