package com.betagames.controller;


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

import com.betagames.dto.CategoriesDTO;
import com.betagames.request.CategoriesRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.ICategoriesService;

/**
 *
 * @author Cristhian Guerrero
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional
public class CategoriesControllerTest {

    private CategoriesRequest categoriesRequest = new CategoriesRequest();

    @Autowired
    private CategoriesController categoriesController;

    @Autowired
    private ICategoriesService categoriesService;


    @BeforeEach
    public void setUp() throws Exception {
        categoriesRequest.setName("Terror");
        categoriesService.create(categoriesRequest);
    }

    @Test
    @Order(1)
    public void testList() {
        ResponseList<CategoriesDTO> r = categoriesController.list();
        Assertions.assertThat(r.getRc()).isEqualTo(true);
        Assertions.assertThat(r.getData().get(0).getName()).isEqualTo("Terror");
    }

    /*
     * @Test
     * 
     * @Order(2)
     * public void testListError() throws Exception {
     * List<CategoriesDTO> lCategoriesDTOs = categoriesService.list();
     * lCategoriesDTOs.forEach(System.out::println);
     * categoriesRequest.setId(1);
     * categoriesService.delete(categoriesRequest);
     * ResponseList<CategoriesDTO> r = categoriesController.list();
     * if (r.getData().size()==0) {
     * r.setRc(false);
     * }
     * System.out.println(r.getMsg());
     * Assertions.assertThat(r.getRc()).isEqualTo(false);
     * Assertions.assertThat(r.getMsg()).isEqualTo(null);
     * }
     */

    @Test
    @Order(2)
    public void testSearchByTyping() throws Exception {
        ResponseList<CategoriesDTO> r = categoriesController.searchByTyping(null, null);
        Assertions.assertThat(r.getRc()).isEqualTo(true);
        Assertions.assertThat(r.getData().get(0).getName()).isEqualTo("Terror");
    }

    @Test
    @Order(3)
    public void testSearchByTypingError() throws Exception {
        ResponseList<CategoriesDTO> r = categoriesController.searchByTyping(200, "hola");
        Assertions.assertThat(r.getRc()).isTrue();
        Assertions.assertThat(r.getData()).isNullOrEmpty();
    }

    @Test
    @Order(4)
    public void testCreate() {
        categoriesRequest.setName("Aventura");
        ResponseBase r = categoriesController.create(categoriesRequest);
        Assertions.assertThat(r.getRc()).isEqualTo(true);
        Assertions.assertThat(r.getMsg()).isEqualTo("Successfully created Category");
        ResponseList<CategoriesDTO> rList = categoriesController.list();
        Assertions.assertThat(rList.getData().size()).isEqualTo(2);
    }

    @Test
    @Order(5)
    public void testCreateError() {
        ResponseBase r = categoriesController.create(null);
        System.out.println(r.getMsg());
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo(
                "Cannot invoke \"com.betagames.request.CategoriesRequest.getName()\" because \"req\" is null");
        ResponseList<CategoriesDTO> rList = categoriesController.list();
        Assertions.assertThat(rList.getData().size()).isEqualTo(1);
    }

    @Test
    @Order(6)
    public void testUpdate() {
        categoriesRequest.setId(7);
        categoriesRequest.setName("Update");

        ResponseBase r = categoriesController.update(categoriesRequest);
        Assertions.assertThat(r.getMsg()).isEqualTo("Successfully updated Category");
        Assertions.assertThat(r.getRc()).isTrue();

    }

    @Test
    @Order(7)
    public void testUpdateError() {
        categoriesRequest.setId(779);
        categoriesRequest.setName("Update");

        ResponseBase r = categoriesController.update(categoriesRequest);
        System.out.println(r.getMsg());
        Assertions.assertThat(r.getRc()).isFalse();
        Assertions.assertThat(r.getMsg()).isEqualTo("The Category isn't present in DB");
    }

    @Test
    @Order(8)
    public void testDelete() {
        categoriesRequest.setId(9);
        categoriesRequest.setName("Terror");
        ResponseBase r = categoriesController.delete(categoriesRequest);
        Assertions.assertThat(r.getMsg()).isEqualTo("Successfully deleted category");
        Assertions.assertThat(r.getRc()).isTrue();
    }

    @Test
    @Order(9)
    public void testDeleteError() {
        categoriesRequest.setId(999);
        categoriesRequest.setName("Terror");
        ResponseBase r = categoriesController.delete(categoriesRequest);
        Assertions.assertThat(r.getMsg()).isEqualTo("The category isn't present");
        Assertions.assertThat(r.getRc()).isFalse();
    }
}// class
