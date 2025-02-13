package com.betagames.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.betagames.service.interfaces.IEditorsService;

/**
 *
 * @author FabriniMarco
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class EditorsServiceTest {
    @Autowired
    Logger log;
    @Autowired
    IEditorsService editorsService;

    @Test
    @Order(1)
    public void createEditorsTest() throws Exception {
        EditorsRequest editorsRequest = new EditorsRequest();
        EditorsRequest editorsRequest2 = new EditorsRequest();
        editorsRequest.setName("Editors 1");
        editorsRequest.setWebsite("Editors 1 web");
        editorsRequest2.setName("Editors 2");
        editorsRequest2.setWebsite("Editors 2 web");

        editorsService.create(editorsRequest);
        editorsService.create(editorsRequest2);

        List<EditorsDTO> listEditors = editorsService.list();

        EditorsDTO verificaEditorsDTO = listEditors.stream()
                .filter(e -> "Editors 1".equals(e.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Editor not found"));

        Assertions.assertThat(verificaEditorsDTO.getId()).isEqualTo(1);

        listEditors.forEach(e -> log.debug("TEST create EditorsTest: " + e.toString()));
    }// createEditorsTest

    @Test
    @Order(2)
    public void createEditorsDuplicateNameTest() throws Exception {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setName("Editors 1");

        Exception exception = assertThrows(Exception.class, () -> {
            editorsService.create(editorsRequest);
        });
        log.debug("createEditorsDuplicateNameTest: {}", exception.getMessage());
    }// createEditorsDuplicateNameTest

    @Test
    @Order(3)
    public void createEditorsDuplicateWebmailTest() throws Exception {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setWebsite("Editors 1 web");

        Exception exception = assertThrows(Exception.class, () -> {
            editorsService.create(editorsRequest);
        });
        log.debug("createEditorsDuplicateWebmailTest: {}", exception.getMessage());
    }// createEditorsDuplicateWebmailTest

    @Test
    @Order(4)
    public void updateEditorsTest() throws Exception {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setId(1);
        editorsRequest.setName("Editors 1 update");
        editorsRequest.setWebsite("Editors 1 web update");

        editorsService.update(editorsRequest);

        List<EditorsDTO> listEditors = editorsService.list();

        EditorsDTO verificaEditorsDTO = listEditors.stream()
                .filter(e -> "Editors 1 update".equals(e.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Editor not found"));

        Assertions.assertThat(verificaEditorsDTO.getName()).isEqualTo("Editors 1 update");

        listEditors.forEach(e -> log.debug("TEST update EditorsTest: " + e.toString()));
    }// updateEditorsTest

    @Test
    @Order(5)
    public void updateEditorsDuplicateNameTest() throws Exception {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setId(2);
        editorsRequest.setName("Editors 1 update");

        Exception exception = assertThrows(Exception.class, () -> {
            editorsService.update(editorsRequest);
        });
        log.debug("updateEditorsDuplicateNameTest: {}", exception.getMessage());
    }// updateEditorsDuplicateNameTest

    @Test
    @Order(6)
    public void updateEditorsDuplicateWebmailTest() throws Exception {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setId(2);
        editorsRequest.setWebsite("Editors 1 web update");

        Exception exception = assertThrows(Exception.class, () -> {
            editorsService.update(editorsRequest);
        });
        log.debug("updateEditorsDuplicateWebmailTest: {}", exception.getMessage());
    }// updateEditorsDuplicateWebmailTest

    @Test
    @Order(7)
    public void updateEditorsNotIdTest() throws Exception {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setId(100);

        Exception exception = assertThrows(Exception.class, () -> {
            editorsService.update(editorsRequest);
        });
        log.debug("updateEditorsNotIdTest: {}", exception.getMessage());
    }// updateEditorsDuplicateWebmailTest

    @Test
    @Order(8)
    public void searchByTypingEditorsTest() throws Exception {
        List<EditorsDTO> listEditors = editorsService.searchByTyping(null, "E", null);

        Assertions.assertThat(listEditors.get(0).getName()).isEqualTo("Editors 1 update");

        listEditors.forEach(e -> log.debug("TEST searchByTyping EditorsTest: " + e.toString()));
    }// searchByTypingEditorsTest

    @Test
    @Order(9)
    public void deleteEditorsNotIdTest() throws Exception {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setId(100);

        Exception exception = assertThrows(Exception.class, () -> {
            editorsService.delete(editorsRequest);
        });
        log.debug("deleteEditorsNotIdTest: {}", exception.getMessage());
    }// deleteEditorsNotIdTest

    @Test
    @Order(10)
    public void deleteEditorsTest() throws Exception {
        EditorsRequest editorsRequest = new EditorsRequest();
        editorsRequest.setId(2);

        editorsService.delete(editorsRequest);

        List<EditorsDTO> listEditors = editorsService.list();

        Assertions.assertThat(listEditors.size()).isEqualTo(1);

        listEditors.forEach(e -> log.debug("TEST delete EditorsTest: " + e.toString()));

    }// deleteEditorsTest

}// class
