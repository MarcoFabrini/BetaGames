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

import com.betagames.controller.DetailsOrderController;
import com.betagames.controller.OrdersController;
import com.betagames.dto.DetailsCartDTO;
import com.betagames.dto.DetailsOrderDTO;
import com.betagames.dto.EditorsDTO;
import com.betagames.dto.GamesDTO;
import com.betagames.dto.OrdersDTO;
import com.betagames.dto.PayCardsDTO;
import com.betagames.dto.RolesDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.request.DetailsCartRequest;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.request.OrdersRequest;
import com.betagames.request.PayCardsRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderController {

    @Autowired
    private IOrdersService ordersService;
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
    OrdersController ordersController;

    @Autowired
    DetailsOrderController detailsOrderController;

    @Test
    @Order(1)
    public void createControllerTest() throws Exception {
        // -----Request------

        OrdersRequest orderRequest = new OrdersRequest();

        PayCardsRequest payCardsRequest = new PayCardsRequest();

        UsersRequest usersRequest = new UsersRequest();

        RolesRequest rolesRequest = new RolesRequest();

        EditorsRequest editorsRequest = new EditorsRequest();

        GamesRequest gamesRequest = new GamesRequest();

        DetailsCartRequest detailsCartRequest = new DetailsCartRequest();

        DetailsCartRequest detailsCartRequestTwo = new DetailsCartRequest();

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
        payCardsRequest.setCardNumber(11223344);
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
        gamesRequest.setEditorsId(listEditors.get(0).getId());

        gamesService.create(gamesRequest);
        List<GamesDTO> listGames = gamesService.list();

        Assertions.assertThat((listGames.size())).isEqualTo(1);

        // -----------Create DetailsCart---------
        detailsCartRequest.setCartId(creaUsersDTO.getCarts().getId());
        detailsCartRequest.setGameId(listGames.get(0).getId());
        detailsCartRequest.setPriceAtTime(12.56);
        detailsCartRequest.setQuantity(2);

        detailsCartRequestTwo.setCartId(creaUsersDTO.getCarts().getId());
        detailsCartRequestTwo.setGameId(listGames.get(0).getId());
        detailsCartRequestTwo.setPriceAtTime(12.56);
        detailsCartRequestTwo.setQuantity(2);

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

        ResponseBase res = ordersController.create(orderRequest);
        Assertions.assertThat(res.getRc()).isEqualTo(true);

        Assertions.assertThat(res.getMsg()).isEqualTo("Ordine Creato con successo");
        List<OrdersDTO> listOrder = ordersService.findAllOrders();

        Assertions.assertThat((listOrder.size())).isEqualTo(1);
    }

    @Test
    @Order(2)
    public void updateTest() throws Exception {

        OrdersRequest orderRequest = new OrdersRequest();
        orderRequest.setId(1);
        orderRequest.setOrderStatus("shipped");

        ResponseBase res = ordersController.update(orderRequest);

        Assertions.assertThat(res.getRc()).isEqualTo(true);

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
    public void listTest() {
        ResponseList<OrdersDTO> res = ordersController.listOrders();

        Assertions.assertThat(res.getRc()).isTrue();
        Assertions.assertThat(res.getData().get(0).getId()).isEqualTo(1);
    }

    @Test
    @Order(4)
    public void listByUser() {
        ResponseList<OrdersDTO> res = ordersController.listOrdersByUsers(1);

        Assertions.assertThat(res.getRc()).isTrue();
        Assertions.assertThat(res.getData().get(0).getOrderStatus()).isEqualTo("shipped");
    }

    @Test
    @Order(5)
    public void searchByTypingId() {
        ResponseList<OrdersDTO> res = ordersController.searchByTyping(1, null, null);

        Assertions.assertThat(res.getRc()).isTrue();
        Assertions.assertThat(res.getData().get(0).getId()).isEqualTo(1);
    }

    @Test
    @Order(8)
    public void readDetailsOrder() throws Exception {

        ResponseList<DetailsOrderDTO> res = detailsOrderController.listDetailsOrder(1);

        Assertions.assertThat(res.getRc()).isTrue();
        Assertions.assertThat(res.getData().get(0).getId()).isEqualTo(1);
    }
}
