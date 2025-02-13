package com.betagames.controller;

import static com.betagames.utility.Utilities.convertDateToString;
import java.util.Date;

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
import com.betagames.request.DetailsCartRequest;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IGamesService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DetailsCartsControllerTest {
    
    @Autowired
    private DetailsCartsController dCController;

    @Autowired
    Logger log;

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
        globalGamesRequest.setPrice(50.00);
        globalGamesRequest.setStockQuantity(12);
        globalGamesRequest.setEditorsId(1);
        gamesService.create(globalGamesRequest);
    }// game


    @Test
	@Order(2)
	public void listDetailsCartsTest() throws Exception{
		ResponseList<DetailsCartDTO> res = dCController.list();
		
		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getData().size()).isEqualTo(1);
	}
    @Test
	@Order(1)
	public void createDetailsCartsTest() throws Exception{
		user();
		game();
        DetailsCartRequest req = new DetailsCartRequest();
        req.setGameId(1);
        req.setCartId(1);
        req.setQuantity(2);

		ResponseBase res = dCController.create(req);
		
		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getMsg()).isEqualTo("Successfully created detailsCart");
	}

    @Test
	@Order(3)
	public void listByCartsDetailsCartsTest() throws Exception{

		ResponseList<DetailsCartDTO> res = dCController.listByCarts(1);
		
		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getData().size()).isEqualTo(1);

        res.getData().forEach(x->{
            log.debug("---------------------------: {}" + x.getId());
        });
    
	}

    @Test
	@Order(4)
	public void listByCartsDetailsCartsExceptionTest() throws Exception{
		ResponseList<DetailsCartDTO> res = dCController.listByCarts(4);
		
		Assertions.assertThat(res.getRc()).isEqualTo(false);
		Assertions.assertThat(res.getMsg()).isEqualTo("cart not found");
	}

    @Test
	@Order(5)
	public void updateDetailsCartsTest() throws Exception{
        DetailsCartRequest req = new DetailsCartRequest();
        req.setId(1);
        req.setQuantity(10);

		ResponseBase res = dCController.update(req);
		//non torna
		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getMsg()).isEqualTo("Successfully updated detailsCart");
	}
    @Test
	@Order(6)
	public void updateDetailsCartsExceptionTest() throws Exception{
        DetailsCartRequest req = new DetailsCartRequest();
        req.setId(300);
        req.setQuantity(10);

		ResponseBase res = dCController.update(req);

		Assertions.assertThat(res.getRc()).isEqualTo(false);
		Assertions.assertThat(res.getMsg()).isEqualTo("details not found");

	}
    @Test
	@Order(7)
	public void deleteDetailsCartsExceptionTest() throws Exception{
        DetailsCartRequest req = new DetailsCartRequest();
        req.setId(3);

		ResponseBase res = dCController.delete(req);
		
		Assertions.assertThat(res.getRc()).isEqualTo(false);
		Assertions.assertThat(res.getMsg()).isEqualTo("details not found");
	}
    @Test
	@Order(8)
	public void deleteDetailsCartsTest() throws Exception{
        DetailsCartRequest req = new DetailsCartRequest();
        req.setId(1);

		ResponseBase res = dCController.delete(req);
		//non torna
		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getMsg()).isEqualTo("Successfully updated detailsCart");
	}
    @Test
	@Order(9)
	public void deleteAllByCartDetailsCartsExceptionTest() throws Exception{

		ResponseBase res = dCController.deleteAllByCart(10);
		//non torna
		Assertions.assertThat(res.getRc()).isEqualTo(false);
		Assertions.assertThat(res.getMsg()).isEqualTo("cart not found");
	}
    @Test
	@Order(10)
	public void deleteAllByCartDetailsCartsTest() throws Exception{

		ResponseBase res = dCController.deleteAllByCart(1);
		//non torna
		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getMsg()).isEqualTo("Successfully updated detailsCart");
	}
   
}
