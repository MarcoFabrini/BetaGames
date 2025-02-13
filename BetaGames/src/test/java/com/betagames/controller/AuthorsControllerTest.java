package com.betagames.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.AuthorsDTO;
import com.betagames.request.AuthorsRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IAuthorsService;

/**
 *
 * @author Cristhian Guerrero
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class AuthorsControllerTest {

    private AuthorsRequest authorsRequest = new AuthorsRequest();

    @Autowired
    private AuthorsController authorsController;

    @Autowired
    private IAuthorsService authorsService;

    @BeforeEach
    public void setUp() throws Exception {
        authorsRequest.setName("authorName");
        authorsRequest.setLastname("authorLastaname");
        authorsRequest.setBiography("BioAuthor");
        authorsRequest.setCountry("Italia");

        authorsService.create(authorsRequest);
    }

    @Test
    @Order(1)
    public void testList() {
        ResponseList<AuthorsDTO> r = authorsController.list();
        Assertions.assertThat(r.getRc()).isTrue();
        Assertions.assertThat(r.getData().get(0).getName()).isEqualTo("authorName");
        Assertions.assertThat(r.getData().get(0).getLastname()).isEqualTo("authorLastaname");
        Assertions.assertThat(r.getData().get(0).getBiography()).isEqualTo("BioAuthor");
        Assertions.assertThat(r.getData().get(0).getCountry()).isEqualTo("Italia");
    }

    @Test
    @Order(2)
    public void testSearchByTyping() throws Exception {
        List<AuthorsDTO> lAuthorsDTOs = authorsService.searchByTyping(null, null, null, null, null);
        lAuthorsDTOs.forEach(System.out::println);
        ResponseList<AuthorsDTO> r = authorsController.searchByTyping(2, null, null, null, null);
        Assertions.assertThat(r.getRc()).isTrue();
        Assertions.assertThat(r.getData().get(0).getName()).isEqualTo("authorName");
        Assertions.assertThat(r.getData().get(0).getLastname()).isEqualTo("authorLastaname");
        Assertions.assertThat(r.getData().get(0).getBiography()).isEqualTo("BioAuthor");
        Assertions.assertThat(r.getData().get(0).getCountry()).isEqualTo("Italia");
    }

    @Test
    @Order(3)
    public void testSearchByTypingError() throws Exception {
        ResponseList<AuthorsDTO> r = authorsController.searchByTyping(3000, "ssss", "ssss", "ssss", "ssss");
        Assertions.assertThat(r.getRc()).isTrue();
        Assertions.assertThat(r.getData()).isNullOrEmpty();
    }

    @Test
    @Order(4)
    public void testCreate() {
        authorsRequest.setName("authorName2");
        authorsRequest.setLastname("authorLastaname2");
        authorsRequest.setBiography("BioAuthor2");
        authorsRequest.setCountry("Italia2");
        ResponseBase r = authorsController.create(authorsRequest);
        Assertions.assertThat(r.getRc()).isTrue();
        Assertions.assertThat(r.getMsg()).isEqualTo("Successfully created Author");
        ResponseList<AuthorsDTO> rList = authorsController.list();
        Assertions.assertThat(rList.getData().size()).isEqualTo(2);
    }

    @Test
    @Order(5)
    public void testCreateError() {
        ResponseBase r = authorsController.create(authorsRequest);
        System.out.println(r.getMsg());
        Assertions.assertThat(r.getRc()).isFalse();
        Assertions.assertThat(r.getMsg()).isEqualTo("This authors name and lastname is present");
        ResponseList<AuthorsDTO> rList = authorsController.list();
        Assertions.assertThat(rList.getData().size()).isEqualTo(1);
    }

    @Test
    @Order(6)
    public void testCreateWithIdGame() {
        authorsRequest.setName("authorName2");
        authorsRequest.setLastname("authorLastaname2");
        authorsRequest.setBiography("BioAuthor2");
        authorsRequest.setCountry("Italia2");
        authorsRequest.setGameId(1);
        ResponseBase r = authorsController.create(authorsRequest);
        Assertions.assertThat(r.getRc()).isTrue();
        Assertions.assertThat(r.getMsg()).isEqualTo("Successfully created Author");
        ResponseList<AuthorsDTO> rList = authorsController.list();
        Assertions.assertThat(rList.getData().size()).isEqualTo(2);
    }

    @Test
    @Order(7)
    public void testUpdate() {
        authorsRequest.setBiography("BioAuthor2");
        authorsRequest.setCountry("Italia2");
        ResponseBase r = authorsController.update(authorsRequest);
        Assertions.assertThat(r.getRc()).isTrue();
        Assertions.assertThat(r.getMsg()).isEqualTo("Successfully updated Author");
        ResponseList<AuthorsDTO> rList = authorsController.list();
        Assertions.assertThat(rList.getData().size()).isEqualTo(1);
    }

    @Test
    @Order(6)
    public void testUpdateError() {
        authorsRequest.setName("authorName2");
        authorsRequest.setLastname("authorLastaname2");
        authorsRequest.setBiography("BioAuthor2");
        authorsRequest.setCountry("Italia2");
        ResponseBase r = authorsController.update(authorsRequest);
        Assertions.assertThat(r.getRc()).isFalse();
        Assertions.assertThat(r.getMsg()).isEqualTo("This authors name or lastname isn't present");
        ResponseList<AuthorsDTO> rList = authorsController.list();
        Assertions.assertThat(rList.getData().size()).isEqualTo(1);
    }

    @Test
    @Order(7)
    public void testDelete() throws Exception {
        List<AuthorsDTO> lAuthorsDTOs = authorsService.searchByTyping(null, null, null, null, null);
        lAuthorsDTOs.forEach(System.out::println);
        authorsRequest.setId(10);
        ResponseBase r = authorsController.delete(authorsRequest);
        Assertions.assertThat(r.getMsg()).isEqualTo("Successfully deleted author");
        Assertions.assertThat(r.getRc()).isTrue();
        ResponseList<AuthorsDTO> rList = authorsController.list();
        Assertions.assertThat(rList.getRc()).isTrue();
        Assertions.assertThat(rList.getData()).isEmpty();
    }
}
