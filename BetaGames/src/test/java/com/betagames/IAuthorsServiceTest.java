package com.betagames;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.betagames.request.AuthorsRequest;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.service.interfaces.IAuthorsService;
import com.betagames.service.interfaces.IDetailsOrderService;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IGamesService;
import com.betagames.service.interfaces.IOrdersService;
import com.betagames.service.interfaces.IPayCardsService;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

import jakarta.transaction.Transactional;

/**
 *
 * @author Cristhian Guerrero
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
public class IAuthorsServiceTest {

    @Autowired
    private IAuthorsService authorsService;
    @Autowired
    private IGamesService gamesService;
    @Autowired
    private IEditorsService editorsService;
    @Autowired
    private static Logger log;

    @BeforeAll
    public static void initAll() {
        log.debug("Start the App");
    }

    @BeforeEach
    public void setUp() {
        EditorsRequest editorsRequest = new EditorsRequest();
        GamesRequest gamesRequest = new GamesRequest();
        AuthorsRequest authorsRequest = new AuthorsRequest();

        editorsRequest.setName("editorsName");
        editorsRequest.setWebsite("website");

        try {
            editorsService.create(editorsRequest);
            log.debug("Succes Created Editor");
        } catch (Exception e) {
            log.error("Error con editor Service: " + e.getMessage());
        }

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

        try {
            gamesService.create(gamesRequest);
            log.debug("Succes Created Game");
        } catch (Exception e) {
            log.error("Error Game Service: " + e.getMessage());
        }

        authorsRequest.setName("authorName");
        authorsRequest.setLastname("authorLastaname");
        

    }

    @Test
    void testCreate() {

    }

    @Test
    void testDelete() {

    }

    @Test
    void testList() {

    }

    @Test
    void testSearchByTyping() {

    }

    @Test
    void testUpdate() {

    }
}
