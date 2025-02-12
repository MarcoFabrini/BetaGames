package com.betagames;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

import com.betagames.dto.DetailsCartDTO;
import com.betagames.dto.EditorsDTO;
import com.betagames.dto.GamesDTO;
import com.betagames.dto.PayCardsDTO;
import com.betagames.dto.RolesDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.request.DetailsCartRequest;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.request.PayCardsRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IDetailsCartsService;
import com.betagames.service.interfaces.IDetailsOrderService;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IGamesService;
import com.betagames.service.interfaces.IOrdersService;
import com.betagames.service.interfaces.IPayCardsService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DetailsCartsServiceTest {

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

    @Order(1)
    @Test
    public void createTest() throws Exception {

        PayCardsRequest payCardsRequest = new PayCardsRequest();

        UsersRequest usersRequest = new UsersRequest();

        RolesRequest rolesRequest = new RolesRequest();

        EditorsRequest editorsRequest = new EditorsRequest();

        GamesRequest gamesRequest = new GamesRequest();

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
        gamesRequest.setPrice(49.99);
        gamesRequest.setStockQuantity(12);
        gamesRequest.setEditorsId(1);

        gamesService.create(gamesRequest);
        List<GamesDTO> lstGames = gamesService.list();

        Assertions.assertThat((lstGames.size())).isEqualTo(1);

    }
    //=========================CREATE==============================
    @Order(2)
    @Test
    public void createDetailsCartsTest() throws Exception {

        DetailsCartRequest detailsCartRequest = new DetailsCartRequest();
        detailsCartRequest.setCartId(1);
        detailsCartRequest.setGameId(1);
        detailsCartRequest.setQuantity(2);

        detailsCartsService.create(detailsCartRequest);

        List<DetailsCartDTO> listDetailsCart = detailsCartsService.list();

        Assertions.assertThat((listDetailsCart.size())).isEqualTo(1);
    }
    @Order(3)
    @Test
    public void createDetailsCartsExceptionGameIdnotPresentTest() throws Exception {
        //gestisco l'eccezione di un game id non presente
        DetailsCartRequest detailsCartRequest2 = new DetailsCartRequest();
        detailsCartRequest2.setCartId(1);
        detailsCartRequest2.setGameId(2);
        detailsCartRequest2.setQuantity(2);

        assertThrows(Exception.class, ()->{
			detailsCartsService.create(detailsCartRequest2);
		});
    }

    @Order(4)
    @Test
    public void createDetailsCartsExceptioDuplicateGameIdTest() throws Exception {

        //gestisco l'eccezione di un game id duplicato
        DetailsCartRequest detailsCartRequest3 = new DetailsCartRequest();
        detailsCartRequest3.setCartId(1);
        detailsCartRequest3.setGameId(1);
        detailsCartRequest3.setQuantity(2);

        assertThrows(Exception.class, ()->{
			detailsCartsService.create(detailsCartRequest3);
		});
    }
    //==================================UPDATE=======================================
    @Order(5)
    @Test
    public void updateDetailsCartsExceptionDetailsCartIdTest() throws Exception {
        //eccezione id detailsCart non presente
        DetailsCartRequest detailsCartRequest = new DetailsCartRequest();
        detailsCartRequest.setId(2);
        detailsCartRequest.setGameId(1);
        detailsCartRequest.setQuantity(10);
        
        assertThrows(Exception.class, ()->{
			detailsCartsService.update(detailsCartRequest);
		});
    }
        //eccezione di un game id non presente
        // DetailsCartRequest detailsCartRequest3 = new DetailsCartRequest();
        // detailsCartRequest3.setId(1);
        // detailsCartRequest3.setGameId(2);
        // detailsCartRequest3.setQuantity(10);
        
        // assertThrows(Exception.class, ()->{
		// 	detailsCartsService.update(detailsCartRequest3);
		// });

        //eccezione di un card id non presente
        // DetailsCartRequest detailsCartRequest4 = new DetailsCartRequest();
        // detailsCartRequest4.setId(1);
        // detailsCartRequest4.setGameId(2);
        // detailsCartRequest4.setQuantity(10);
        
        // assertThrows(Exception.class, ()->{
		// 	detailsCartsService.update(detailsCartRequest4);
		// });

        // List<DetailsCartDTO> listDetailsCart = detailsCartsService.list();
        // Assertions.assertThat((listDetailsCart.get(0).getQuantity())).isEqualTo(2);


    @Order(6)
    @Test
    public void updateDetailsCartsTest() throws Exception {
        //update corretto
        DetailsCartRequest detailsCartRequest2 = new DetailsCartRequest();
        detailsCartRequest2.setId(1);
        detailsCartRequest2.setGameId(1);
        detailsCartRequest2.setQuantity(3);

        detailsCartsService.update(detailsCartRequest2);

        List<DetailsCartDTO> listDetailsCart = detailsCartsService.list();
        Assertions.assertThat((listDetailsCart.get(0).getQuantity())).isEqualTo(3);
        Assertions.assertThat((listDetailsCart.get(0).getPriceAtTime())).isEqualTo(149.97);

    }
    //============================DELETE================================
    @Order(7)
    @Test
    public void deleteDetailsCartsExceptionIdTest() throws Exception {
        //eccezione detailsCart id non presente
        DetailsCartRequest detailsCartRequest = new DetailsCartRequest();
        detailsCartRequest.setId(2);

        assertThrows(Exception.class, ()->{
			detailsCartsService.delete(detailsCartRequest);
		});

        List<DetailsCartDTO> listDetailsCart = detailsCartsService.list();
        Assertions.assertThat((listDetailsCart.size())).isEqualTo(1);
    }
    @Order(8)
    @Test
    public void deleteDetailsCartsTest() throws Exception {
        //delete corretto
        DetailsCartRequest detailsCartRequest2 = new DetailsCartRequest();
        detailsCartRequest2.setId(1);

        detailsCartsService.delete(detailsCartRequest2);

        List<DetailsCartDTO> listDetailsCart2 = detailsCartsService.list();
        Assertions.assertThat((listDetailsCart2.size())).isEqualTo(0);
    }
    //======================DELETE ALL=========================
    @Order(9)
    @Test
    public void deleteAllByCartDetailsExceptionIdCartsTest() throws Exception {
        //eccezione detailsCart id non presente
        Integer id = 2;

        assertThrows(Exception.class, ()->{
			detailsCartsService.deleteAllByCart(id);
		});
    }
    @Order(10)
    @Test
    public void deleteAllByCartDetailsCartsTest() throws Exception {
        //delete all corretto
        Integer id2 = 1;

        detailsCartsService.deleteAllByCart(id2);

        List<DetailsCartDTO> listDetailsCart2 = detailsCartsService.list();
        Assertions.assertThat((listDetailsCart2.size())).isEqualTo(0);
    }
    //======================LIST BY CART=========================
    @Order(11)
    @Test
    public void listByCartsDetailsCartsExceptionIdTest() throws Exception {
        //eccezione detailsCart id non presente
        Integer id = 2;

        assertThrows(Exception.class, ()->{
			detailsCartsService.listByCarts(id);
		});
    }
    @Order(12)
    @Test
    public void listByCartsDetailsCartsTest() throws Exception {
        //delete all corretto
        Integer id2 = 1;

        detailsCartsService.listByCarts(id2);

        List<DetailsCartDTO> listDetailsCart2 = detailsCartsService.list();
        Assertions.assertThat((listDetailsCart2.size())).isEqualTo(0);
    }
    
}
