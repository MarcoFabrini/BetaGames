package com.betagames;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.description;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.GamesDTO;
import com.betagames.request.AuthorsRequest;
import com.betagames.request.CategoriesRequest;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.request.ReviewsRequest;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IAuthorsService;
import com.betagames.service.interfaces.ICategoriesService;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IGamesService;
import com.betagames.service.interfaces.IReviewsService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

/**
 *
 * @author Cristhian Guerrero
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
public class IGamesServicesTest {
    
    private EditorsRequest editorsRequest = new EditorsRequest();
    private GamesRequest gamesRequest = new GamesRequest();
    private AuthorsRequest authorsRequest = new AuthorsRequest();
    private CategoriesRequest categoriesRequest = new CategoriesRequest();
    private ReviewsRequest reviewsRequest = new ReviewsRequest();
    private UsersRequest usersRequest = new UsersRequest();
    private RolesRequest rolesRequest = new RolesRequest();

    @Autowired
    private IAuthorsService authorsService;
    @Autowired
    private IGamesService gamesService;
    @Autowired
    private IEditorsService editorsService;
    @Autowired
    private ICategoriesService categoriesService;
    @Autowired
    private IRolesService rolesService;
    @Autowired
    private IUsersService usersService;
    @Autowired
    private IReviewsService reviewsService;

    @BeforeEach
    public void setUp() throws Exception {

        editorsRequest.setName("editorsName");
        editorsRequest.setWebsite("website");

        editorsService.create(editorsRequest);

        rolesRequest = new RolesRequest();
        rolesRequest.setName("user");

        rolesService.create(rolesRequest);

        usersRequest = new UsersRequest();
        usersRequest.setUsername("userTest");
        usersRequest.setPwd("userTest");
        usersRequest.setEmail("userTest@example.com");
        usersRequest.setActive(true);
        usersRequest.setRoleId(1);
        usersService.createUser(usersRequest);

        categoriesRequest.setName("Terror");

        categoriesService.create(categoriesRequest);

        authorsRequest.setName("authorName");
        authorsRequest.setLastname("authorLastaname");
        authorsRequest.setBiography("Bio Author");
        authorsRequest.setCountry("Italia");

        authorsService.create(authorsRequest);

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

        reviewsRequest.setScore(5);
        reviewsRequest.setDescription("description review");
        reviewsRequest.setCreatedAt(new Date());
        reviewsRequest.setUsersId(1);
        reviewsRequest.setGameId(1);
        reviewsService.create(reviewsRequest);

    }

    @Test
    @Order(1)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreate() throws Exception {

        List<GamesDTO> lGamesDTOs = gamesService.list();
        lGamesDTOs.forEach(System.out::println);
        Assertions.assertThat(lGamesDTOs.size()).isEqualTo(1);

        GamesDTO gamesDTO = lGamesDTOs.stream()
                .filter(g -> "Giochino".equals(g.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

        Assertions.assertThat(gamesDTO.getId()).isEqualTo(1);
    }

    @Test
    @Order(2)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateAllDati() throws Exception {
        gamesRequest.setAuthorsId(1);
        gamesRequest.setCategoryId(1);
        gamesRequest.setReviewsId(1);
        gamesRequest.setName("Gio");
        gamesService.create(gamesRequest);

        List<GamesDTO> lGamesDTOs = gamesService.list();
        lGamesDTOs.forEach(System.out::println);

        Assertions.assertThat(lGamesDTOs.size()).isEqualTo(2);

        GamesDTO gamesDTO = lGamesDTOs.stream()
                .filter(g -> "Gio".equals(g.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

        Assertions.assertThat(gamesDTO.getId()).isEqualTo(2);
    }

    @Test
    @Order(4)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testCreateError() throws Exception {
        assertThrows(Exception.class, () -> {
            gamesService.create(gamesRequest);
        });
    }

    @Test
    @Order(5)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdate() throws Exception {
        gamesRequest.setId(1);
        gamesRequest.setDescription("Descrizione del giochino update");

        gamesService.update(gamesRequest);

        List<GamesDTO> lGamesDTOs = gamesService.list();
        lGamesDTOs.forEach(System.out::println);

        Assertions.assertThat(lGamesDTOs.size()).isEqualTo(1);

        GamesDTO gamesDTO = lGamesDTOs.stream()
                .filter(g -> "Giochino".equals(g.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

        Assertions.assertThat(gamesDTO.getId()).isEqualTo(1);
    }

    @Test
    @Order(6)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateAllDati() throws Exception {
        gamesRequest.setId(1);
        gamesRequest.setAuthorsId(1);
        gamesRequest.setCategoryId(1);
        gamesRequest.setReviewsId(1);
        gamesRequest.setDescription("Descrizione del giochino update");

        gamesService.update(gamesRequest);

        List<GamesDTO> lGamesDTOs = gamesService.list();
        lGamesDTOs.forEach(System.out::println);

        Assertions.assertThat(lGamesDTOs.size()).isEqualTo(1);

        GamesDTO gamesDTO = lGamesDTOs.stream()
                .filter(g -> "Giochino".equals(g.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

        Assertions.assertThat(gamesDTO.getId()).isEqualTo(1);
    }

    @Test
    @Order(7)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateError() throws Exception {
        gamesRequest.setId(500);

        assertThrows(Exception.class, ()->{
            gamesService.update(gamesRequest);
        });
    }

    @Test
    @Order(8)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDelete() throws Exception {
        gamesRequest.setId(1);

        gamesService.delete(gamesRequest);

        assertThrows(Exception.class, () -> {
            List<GamesDTO> lGamesDTOs = gamesService.list();
        });
    }

    @Test
    @Order(9)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testDeleteError() throws Exception {
        gamesRequest.setId(300);

        assertThrows(Exception.class, () -> {
            gamesService.delete(gamesRequest);
        });
    }

}
