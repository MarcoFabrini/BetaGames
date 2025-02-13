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

import com.betagames.controller.GamesController;
import com.betagames.dto.GamesDTO;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.ICategoriesService;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IGamesService;

/**
 * @author DorigoLorenzo
 **/

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GamesControllerTest {

 @Autowired
    GamesController gamesController;

    @Autowired
    private IGamesService gamesService;

    @Autowired
    private IEditorsService editorsService;

    @Autowired
    private ICategoriesService categoriesService;


    @Autowired
    Logger log;

    /*
     * Metodi del controller:
     *  create
     *  list
     *  update
     *  delete
    */

    @Test
    @Order(1)
    void createTest() throws Exception {
        // setup
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setName("Test Editor");
        editorsRequest.setWebsite("www.test.it");
        editorsService.create(editorsRequest);

        GamesRequest gamesRequest = new GamesRequest();
        gamesRequest.setName("Test Game");
        gamesRequest.setDescription("Test Description");
        gamesRequest.setPrice(20.0);
        gamesRequest.setStockQuantity(12);
        gamesRequest.setMaxGameTime(5);
        gamesRequest.setMinGameTime(2);
        gamesRequest.setMaxPlayerNumber(6);
        gamesRequest.setMinPlayerNumber(2);
        gamesRequest.setMinAge(5);
        gamesRequest.setDate("10/01/2024");
        gamesRequest.setEditorsId(1);

        ResponseBase response = gamesController.create(gamesRequest);

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        //Assertions.assertThat(response.getMsg()).isEqualTo("Game successfully CREATED!");
    }

    @Test
    @Order(2)
    void updateTest() throws Exception {
        GamesRequest gamesRequest = new GamesRequest();
        gamesRequest.setId(1);
        gamesRequest.setName("Update Test Game");
        gamesRequest.setDescription("Updated Test Description");
        gamesRequest.setPrice(19.99);
        gamesRequest.setStockQuantity(15);
        gamesRequest.setMaxGameTime(5);
        gamesRequest.setMinGameTime(2);
        gamesRequest.setMaxPlayerNumber(6);
        gamesRequest.setMinPlayerNumber(2);
        gamesRequest.setMinAge(5);
        gamesRequest.setDate("01/01/2024");
        gamesRequest.setEditorsId(1);

        ResponseBase response = gamesController.update(gamesRequest);

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        //Assertions.assertThat(response.getMsg()).isEqualTo("Game successfully UPDATED!");

    }

    @Test
    @Order(3)
    void deleteTest() throws Exception {
        List<GamesDTO> games = gamesService.list();
        Assertions.assertThat(games).isNotEmpty();
        GamesDTO existingGame = games.get(0);

        GamesRequest gamesRequest = new GamesRequest();
        gamesRequest.setId(existingGame.getId()); // Imposta l'ID del gioco da eliminare

        ResponseBase response = gamesController.delete(gamesRequest);

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        //Assertions.assertThat(response.getMsg()).isEqualTo("Game successfully DELETED!");
    }

    @Test
    @Order(4)
    void listTest() throws Exception {
        ResponseList<GamesDTO> response = gamesController.list();

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        Assertions.assertThat(response.getData()).isNotNull();
    }

    @Test
    @Order(5)
    void createFailTest() throws Exception {
        //Crea una GamesRequest con dati non validi
        GamesRequest gamesRequest = new GamesRequest();
        gamesRequest.setName(null); //Titolo nullo per scatenare l'eccezione

        ResponseBase response = gamesController.create(gamesRequest);

        Assertions.assertThat(response.getRc()).isEqualTo(false);
        Assertions.assertThat(response.getMsg()).isNotNull();//Verifica che il messaggio di errore non sia nullo
    }

    @Test
    @Order(6)
    void updateFailTest() throws Exception {
        //Crea una GamesRequest con dati non validi
        GamesRequest gamesRequest = new GamesRequest();
        gamesRequest.setId(999); //Id non valido per scatenare l'eccezione

        ResponseBase response = gamesController.update(gamesRequest);

        Assertions.assertThat(response.getRc()).isEqualTo(false);
        Assertions.assertThat(response.getMsg()).isNotNull();//Verifica che il messaggio di errore non sia nullo
    }

    @Test
    @Order(7)
    void deleteFailTest() throws Exception {
        //Crea una GamesRequest con dati non validi
        GamesRequest gamesRequest = new GamesRequest();
        gamesRequest.setId(999); //Id non valido per scatenare l'eccezione

        ResponseBase response = gamesController.delete(gamesRequest);

        Assertions.assertThat(response.getRc()).isEqualTo(false);
        Assertions.assertThat(response.getMsg()).isNotNull();//Verifica che il messaggio di errore non sia nullo
    }



}//class