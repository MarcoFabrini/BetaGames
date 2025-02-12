package com.betagames;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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

import com.betagames.dto.AuthorsDTO;
import com.betagames.request.AuthorsRequest;
import com.betagames.request.EditorsRequest;
import com.betagames.request.GamesRequest;
import com.betagames.service.interfaces.IAuthorsService;
import com.betagames.service.interfaces.IEditorsService;
import com.betagames.service.interfaces.IGamesService;

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

    private EditorsRequest editorsRequest = new EditorsRequest();

    private GamesRequest gamesRequest = new GamesRequest();

    private AuthorsRequest authorsRequest = new AuthorsRequest();

    @Autowired
    private IAuthorsService authorsService;
    @Autowired
    private IGamesService gamesService;
    @Autowired
    private IEditorsService editorsService;
    @Autowired
    private Logger log;

    @BeforeEach
    public void setUp() {
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
        authorsRequest.setBiography("Bio Author");
        authorsRequest.setCountry("Italia");
        authorsRequest.setGameId(1);
    }

    @Test
    @Order(1)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testCreateWithoutGame() throws Exception {
        authorsRequest.setGameId(null);
        authorsService.create(authorsRequest);

        List<AuthorsDTO> lAuthorsDTOs = authorsService.list();
        lAuthorsDTOs.forEach(System.out::println);
        AuthorsDTO authorsDTO = lAuthorsDTOs.stream()
                .filter(a -> "authorName".equals(a.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

        Assertions.assertThat(authorsDTO.getId()).isEqualTo(1);

        Assertions.assertThat(lAuthorsDTOs.size()).isEqualTo(1);
    }


    @Test
    @Order(2)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testCreateWithGame() throws Exception {
        authorsService.create(authorsRequest);

        List<AuthorsDTO> lAuthorsDTOs = authorsService.list();
        lAuthorsDTOs.forEach(System.out::println);

        AuthorsDTO authorsDTO = lAuthorsDTOs.stream()
                .filter(a -> "authorName".equals(a.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

        Assertions.assertThat(authorsDTO.getId()).isEqualTo(1);

        Assertions.assertThat(lAuthorsDTOs.size()).isEqualTo(1);
    }

    
    @Test
    @Order(3)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testCreateError() throws Exception {
        authorsRequest.setGameId(null);
        authorsService.create(authorsRequest);

        List<AuthorsDTO> lAuthorsDTOs = authorsService.searchByTyping(null, null, null, null, null);
        lAuthorsDTOs.forEach(System.out::println);

        assertThrows(Exception.class, () -> {
            authorsService.create(authorsRequest);
        });
    }

    @Test
    @Order(4)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testUpdateError() throws Exception {
        authorsRequest.setGameId(null);
        authorsService.create(authorsRequest);

        List<AuthorsDTO> lAuthorsDTOs = authorsService.list();
        lAuthorsDTOs.forEach(System.out::println);
        AuthorsDTO authorsDTO = lAuthorsDTOs.stream()
                .filter(a -> "authorName".equals(a.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

        authorsRequest.setId(authorsDTO.getId());
        authorsRequest.setName("names Update");

        assertThrows(Exception.class, () -> {
            authorsService.update(authorsRequest);
        });
    }

    @Test
    @Order(5)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testUpdate() throws Exception {
        authorsRequest.setGameId(null);
        authorsService.create(authorsRequest);

        List<AuthorsDTO> lAuthorsDTOs = authorsService.list();
        lAuthorsDTOs.forEach(System.out::println);
        AuthorsDTO authorsDTO = lAuthorsDTOs.stream()
                .filter(a -> "authorName".equals(a.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

        authorsRequest.setId(authorsDTO.getId());
        authorsRequest.setGameId(1);
        authorsRequest.setBiography("Gata");
        authorsRequest.setCountry("Colombia");
        

        authorsService.update(authorsRequest);

        authorsDTO = lAuthorsDTOs.stream()
                .filter(a -> "authorName".equals(a.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

        Assertions.assertThat(authorsDTO.getId()).isEqualTo(1);

        Assertions.assertThat(lAuthorsDTOs.size()).isEqualTo(1);
    }

    @Test
    @Order(6)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testDelete() throws Exception {
        authorsRequest.setGameId(null);
        authorsService.create(authorsRequest);

        List<AuthorsDTO> lAuthorsDTOs = authorsService.list();
        lAuthorsDTOs.forEach(System.out::println);
        AuthorsDTO authorsDTO = lAuthorsDTOs.stream()
                .filter(a -> "authorName".equals(a.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

        authorsRequest.setId(authorsDTO.getId());
        authorsService.delete(authorsRequest);

        lAuthorsDTOs = authorsService.list();

        Assertions.assertThat(lAuthorsDTOs.size()).isEqualTo(0);
    }

    @Test
    @Order(7)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testDeleteWithGameIdError() throws Exception {
        authorsService.create(authorsRequest);

        List<AuthorsDTO> lAuthorsDTOs = authorsService.list();
        lAuthorsDTOs.forEach(System.out::println);
        AuthorsDTO authorsDTO = lAuthorsDTOs.stream()
                .filter(a -> "authorName".equals(a.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

        authorsRequest.setId(authorsDTO.getId());

        authorsService.delete(authorsRequest);

        lAuthorsDTOs = authorsService.list();

        Assertions.assertThat(lAuthorsDTOs.size()).isEqualTo(0);
    }

    @Test
    @Order(8)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testDeleteGameIdError() throws Exception {
        authorsService.create(authorsRequest);

        List<AuthorsDTO> lAuthorsDTOs = authorsService.list();
        lAuthorsDTOs.forEach(System.out::println);
        
        authorsRequest.setId(2);
        
        assertThrows(Exception.class, () -> {
            authorsService.delete(authorsRequest);  
        });   
    }
}// test
