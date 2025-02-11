/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.betagames;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.betagames.dto.EditorsDTO;
import com.betagames.request.EditorsRequest;
import com.betagames.service.interfaces.IEditorsService;

/**
 *
 * @author FabriniMarco
 */
@SpringBootTest
@ActiveProfiles(value="test")
public class EditorsServiceTest {
    @Autowired
    Logger log;
    @Autowired
    private IEditorsService editorsS;

    @Test
    public void createTest() throws Exception {
        EditorsRequest editorsR = new EditorsRequest();

        editorsR.setName("editorsName");
        editorsR.setWebsite("website");
        editorsS.create(editorsR);

        List<EditorsDTO> listEditors = editorsS.list();

        EditorsDTO creaEditorsDTO = listEditors.stream()
                .filter(e -> "editorsName".equals(e.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Editor not found"));

        Assertions.assertThat(creaEditorsDTO.getId()).isEqualTo(1);
    }// createTest

}// class
