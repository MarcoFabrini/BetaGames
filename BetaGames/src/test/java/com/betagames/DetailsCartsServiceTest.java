package com.betagames;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.betagames.dto.CartsDTO;
import com.betagames.dto.DetailsCartDTO;
import com.betagames.dto.EditorsDTO;
import com.betagames.dto.GamesDTO;
import com.betagames.dto.RolesDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.model.Carts;
import com.betagames.model.DetailsCart;
import com.betagames.repository.ICartsRepository;
import com.betagames.request.DetailsCartRequest;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.ICartsService;
import com.betagames.service.interfaces.IDetailsCartsService;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IGamesService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DetailsCartsServiceTest {

    @Autowired
    protected TestDataUtils testDataUtil;

    @Autowired
    ICartsRepository cartR;

    @Autowired
    ICartsService cartS;

    @Autowired
    IDetailsCartsService detailsCartS;

    @Autowired
    IGamesService gamesService;

    @Autowired
    IEditorsService editorsService;

    @Autowired
    IUsersService usersS;

    @Autowired
    IRolesService rolesS;

    @BeforeEach
    public void init() {
        testDataUtil.createRoleTest("user");
        testDataUtil.createUserTest("ciccio", "ciccio@gmail.com", "1234");
    }

    @Test
	@Order(1)
    public void createDetailsCartsTest() throws Exception{

        // UsersRequest usersRequest = new UsersRequest();

        // RolesRequest rolesRequest = new RolesRequest();

        // EditorsRequest editorsRequest = new EditorsRequest();

        // GamesRequest gamesRequest = new GamesRequest();

        List<RolesDTO> listRole = rolesS.listRoles();


        Assertions.assertThat(( listRole.get(0).getName() )).isEqualTo("user");
        Assertions.assertThat(( listRole.size() )).isEqualTo(1);

        List<CartsDTO> lCarts = cartS.list();
        List<Carts> lC = cartR.findAll();

        List<UsersDTO> lU = usersS.list();
        lU.forEach(x->{
            System.out.println(x);  
        });
        
        Assertions.assertThat(( lC.get(0).getUser().getId() )).isEqualTo(0);
        Assertions.assertThat(( lC.size() )).isEqualTo(1);

        // ---------create Roles----------
        // rolesRequest.setName("User");
        // rolesService.create(rolesRequest);

        // List<RolesDTO> listRoles = rolesService.listRoles();

        // Assertions.assertThat(listRoles.size()).isEqualTo(1);

        // ---------Create User-------
        // usersRequest.setUsername("userTest");
        // usersRequest.setPwd("userTest");
        // usersRequest.setEmail("userTest@example.com");
        // usersRequest.setRoleId(1);
        // usersService.createUser(usersRequest);

        // List<UsersDTO> listUsers = usersService.searchByTyping(1, "userTest", "userTest@example.com");

        // UsersDTO creaUsersDTO = listUsers.stream()
        //         .filter(e -> "userTest".equals(e.getUsername()))
        //         .findFirst()
        //         .orElseThrow(() -> new AssertionError("User not found"));

        // Assertions.assertThat(creaUsersDTO.getId()).isEqualTo(1);

        // --------Create Editors--------
        // editorsRequest.setName("editorsName");
        // editorsRequest.setWebsite("website");

        // editorsService.create(editorsRequest);

        // List<EditorsDTO> listEditors = editorsService.list();

        // EditorsDTO creaEditorsDTO = listEditors.stream()
        //         .filter(e -> "editorsName".equals(e.getName()))
        //         .findFirst()
        //         // .filter(e -> "website".equals(e.getEmail()))
        //         .orElseThrow(() -> new AssertionError("Editor not found"));

        // Assertions.assertThat(creaEditorsDTO.getId()).isEqualTo(1);

        // // -------Create Games--------
        // gamesRequest.setName("Giochino");
        // gamesRequest.setDescription("Descrizione del giochino");
        // gamesRequest.setDate("01/01/2025");
        // gamesRequest.setMaxGameTime(4);
        // gamesRequest.setMinGameTime(2);
        // gamesRequest.setMaxPlayerNumber(4);
        // gamesRequest.setMinPlayerNumber(2);
        // gamesRequest.setMinAge(10);
        // gamesRequest.setPrice(50.00);
        // gamesRequest.setStockQuantity(12);
        // gamesRequest.setEditorsId(1);

        // gamesService.create(gamesRequest);
        // List<GamesDTO> lstGames = gamesService.list();

        // Assertions.assertThat((lstGames.size())).isEqualTo(1);


        // //DetailsCard Request
        // DetailsCartRequest req = new DetailsCartRequest();
        
        // req.setQuantity(5);
        // req.setGameId(2);
        // req.setCartId(1);



        // detailsCartS.create(req);

        // List<DetailsCartDTO> listDC = detailsCartS.list();

        // Assertions.assertThat(listDC.size()).isGreaterThan(0);

        // DetailsCartDTO createDetailsCart = listDC.stream()
        //     .filter(d->1 = d.getQuantity()) 
        //     .findFirst()
        //     .orElseThrow(()-> new AssertionError("Quantità non trovata"));

        // Assertions.assertThat(createDetailsCart.getGamesDTO()).isEqualTo(2);

        // DetailsCart detailsCart = new DetailsCart();

        // List<RolesDTO> listRoles = rolesService.listRoles();

        // RolesDTO verificaRolesDTO = listRoles.stream()
        //         .filter(e -> "User".equals(e.getName()))
        //         .findFirst()
        //         .orElseThrow(() -> new AssertionError("Roles not found"));
        
        // Assertions.assertThat(verificaRolesDTO.getId()).isEqualTo(1);

        // listRoles.forEach(e -> log.debug("Test: " + e.toString()));
    }
    
}
