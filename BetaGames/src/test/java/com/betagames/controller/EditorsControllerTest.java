package com.betagames.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.betagames.dto.EditorsDTO;
import com.betagames.request.EditorsRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;

/**
 *
 * @author FabriniMarco
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class EditorsControllerTest {

    @Autowired
    Logger log;
    
    @Autowired
    EditorsController editorsController;

    private EditorsRequest editor() throws Exception {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setName("Editors");
        editorsRequest.setWebsite("www.editor@example.com");
        return editorsRequest;
    }// editor

    @Test
    @Order(1)
    void testCreate() throws Exception {
        ResponseBase response = editorsController.create(editor());
        log.debug("testCreate: {} " + response.getMsg());
    }// testCreate

    @Test
    @Order(2)
    void testCreateError() throws Exception {
        ResponseBase response = editorsController.create(editor());
        log.debug("testCreateError: {} " + response.getMsg());
    }// testCreateError

    @Test
    @Order(3)
    void testList() {
        ResponseList<EditorsDTO> response = editorsController.list();

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        Assertions.assertThat(response.getData().get(0).getName()).isEqualToIgnoringCase("Editors");
        Assertions.assertThat(response.getData().get(0).getWebsite()).isEqualToIgnoringCase("www.editor@example.com");
    }// testList

    @Test
    @Order(4)
    void testSearchByTyping() {
        ResponseList<EditorsDTO> response = editorsController.searchByTyping(null, "Editors", null);

        Assertions.assertThat(response.getRc()).isEqualTo(true);
        Assertions.assertThat(response.getData().get(0).getName()).isEqualToIgnoringCase("Editors");
        Assertions.assertThat(response.getData().get(0).getWebsite()).isEqualToIgnoringCase("www.editor@example.com");
    }// testSearchByTyping

    @Test
    @Order(5)
    void testUpdate() {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setId(1);
        editorsRequest.setName("EditorsUpdate");
        editorsRequest.setWebsite("www.editorUpdate@example.com");

        ResponseBase response = editorsController.update(editorsRequest);
        log.debug("testUpdate: {} " + response.getMsg());
    }// testUpdate

    @Test
    @Order(6)
    void testUpdateError() {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setId(100);

        ResponseBase response = editorsController.update(editorsRequest);
        log.debug("testUpdateError: {} " + response.getMsg());
    }// testUpdateError

    @Test
    @Order(7)
    void testDeleteError() {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setId(100);

        ResponseBase response = editorsController.delete(editorsRequest);
        log.debug("testDeleteError: {} " + response.getMsg());
    }// testDeleteError

    @Test
    @Order(8)
    void testDelete() {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setId(1);

        ResponseBase response = editorsController.delete(editorsRequest);
        log.debug("testDelete: {} " + response.getMsg());
    }// testDelete

}// class
