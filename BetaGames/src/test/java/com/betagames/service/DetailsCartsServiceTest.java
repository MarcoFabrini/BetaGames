package com.betagames.service;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.betagames.dto.DetailsCartDTO;
import com.betagames.request.DetailsCartRequest;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IDetailsCartsService;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IGamesService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;
import static com.betagames.utility.Utilities.convertDateToString;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DetailsCartsServiceTest {

    @Autowired
    private IDetailsCartsService detailsCartsService;

    @Autowired
    IRolesService rolesService;
    @Autowired
    IUsersService userService;
	@Autowired
    IEditorsService editorsService;
    @Autowired
    IGamesService gamesService;

    private RolesRequest globalRolesAdminRequest;
    private RolesRequest globalRolesUserRequest;
    private UsersRequest globalAdminRequest;
    private UsersRequest globalUserRequest;
	private EditorsRequest globalEditorsRequest;
    private GamesRequest globalGamesRequest;
    private final Date now = new Date();

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

	private void editor() throws Exception{
        globalEditorsRequest = new EditorsRequest();
        globalEditorsRequest.setName("editorsTest");
        globalEditorsRequest.setWebsite("editorTest@example.com");
        editorsService.create(globalEditorsRequest);
    }// editor

    private void game() throws Exception{
        editor();
        globalGamesRequest = new GamesRequest();
        globalGamesRequest.setName("game test");
        globalGamesRequest.setDescription("description game");
        globalGamesRequest.setDate(convertDateToString(now));
        globalGamesRequest.setMaxGameTime(4);
        globalGamesRequest.setMinGameTime(2);
        globalGamesRequest.setMaxPlayerNumber(4);
        globalGamesRequest.setMinPlayerNumber(2);
        globalGamesRequest.setMinAge(10);
        globalGamesRequest.setPrice(49.99);
        globalGamesRequest.setStockQuantity(12);
        globalGamesRequest.setEditorsId(1);
        gamesService.create(globalGamesRequest);
    }// game
    //=========================CREATE==============================
    @Order(1)
    @Test
    public void createDetailsCartsTest() throws Exception {
        user();
        game();
        DetailsCartRequest detailsCartRequest = new DetailsCartRequest();
        detailsCartRequest.setCartId(1);
        detailsCartRequest.setGameId(1);
        detailsCartRequest.setQuantity(2);

        DetailsCartRequest detailsCartRequest2 = new DetailsCartRequest();
        detailsCartRequest2.setCartId(2);
        detailsCartRequest2.setGameId(1);
        detailsCartRequest2.setQuantity(2);

        detailsCartsService.create(detailsCartRequest);
        List<DetailsCartDTO> listDetailsCart = detailsCartsService.list();
        Assertions.assertThat((listDetailsCart.size())).isEqualTo(1);

        detailsCartsService.create(detailsCartRequest2);
        List<DetailsCartDTO> listDetailsCart2 = detailsCartsService.list();
        Assertions.assertThat((listDetailsCart2.size())).isEqualTo(2);
    }
    @Order(2)
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

    @Order(3)
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
    @Order(4)
    @Test
    public void updateDetailsCartsExceptionDetailsCartIdTest() throws Exception {
        //eccezione id detailsCart non presente
        DetailsCartRequest detailsCartRequest = new DetailsCartRequest();
        detailsCartRequest.setId(30);
        detailsCartRequest.setGameId(1);
        detailsCartRequest.setQuantity(10);
        
        assertThrows(Exception.class, ()->{
			detailsCartsService.update(detailsCartRequest);
		});
    }
        //eccezione di un game id non presente

        //eccezione di un card id non presente

    @Order(5)
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

    }
    //============================DELETE================================
    @Order(6)
    @Test
    public void deleteDetailsCartsExceptionIdTest() throws Exception {
        //eccezione detailsCart id non presente
        DetailsCartRequest detailsCartRequest = new DetailsCartRequest();
        detailsCartRequest.setId(30);

        assertThrows(Exception.class, ()->{
			detailsCartsService.delete(detailsCartRequest);
		});

        List<DetailsCartDTO> listDetailsCart = detailsCartsService.list();
        Assertions.assertThat((listDetailsCart.size())).isEqualTo(2);
    }
    @Order(7)
    @Test
    public void deleteDetailsCartsTest() throws Exception {
        //delete corretto
        DetailsCartRequest detailsCartRequest2 = new DetailsCartRequest();
        detailsCartRequest2.setId(1);

        detailsCartsService.delete(detailsCartRequest2);

        List<DetailsCartDTO> listDetailsCart2 = detailsCartsService.list();
        Assertions.assertThat((listDetailsCart2.size())).isEqualTo(1);
    }
    //======================DELETE ALL=========================
    @Order(8)
    @Test
    public void deleteAllByCartDetailsExceptionIdCartsTest() throws Exception {
        //eccezione detailsCart id non presente
        DetailsCartRequest detailsCartRequest = new DetailsCartRequest();
        detailsCartRequest.setCartId(30);

        assertThrows(Exception.class, ()->{
			detailsCartsService.deleteAllByCart(detailsCartRequest);
		});
    }
    @Order(9)
    @Test
    public void deleteAllByCartDetailsCartsTest() throws Exception {
        //delete all corretto
        DetailsCartRequest detailsCartRequest = new DetailsCartRequest();
        detailsCartRequest.setCartId(1);

        detailsCartsService.deleteAllByCart(detailsCartRequest);

        List<DetailsCartDTO> listDetailsCart = detailsCartsService.listByCarts(1);
        Assertions.assertThat((listDetailsCart.size())).isEqualTo(0);

        List<DetailsCartDTO> listDetailsCart2 = detailsCartsService.list();
        Assertions.assertThat((listDetailsCart2.size())).isEqualTo(1);
    }
    //======================LIST BY CART=========================
    @Order(10)
    @Test
    public void listByCartsDetailsCartsExceptionIdTest() throws Exception {
        //eccezione detailsCart id non presente
        Integer id = 3;

        assertThrows(Exception.class, ()->{
			detailsCartsService.listByCarts(id);
		});
    }
    @Order(11)
    @Test
    public void listByCartsDetailsCartsTest() throws Exception {

        Integer id = 1;

        detailsCartsService.listByCarts(id);

        List<DetailsCartDTO> listDetailsCart2 = detailsCartsService.list();
        Assertions.assertThat((listDetailsCart2.size())).isEqualTo(1);
    }
    
}
