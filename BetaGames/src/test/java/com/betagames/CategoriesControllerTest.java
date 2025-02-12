package com.betagames;

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
import org.springframework.transaction.annotation.Transactional;

import com.betagames.controller.CategoriesController;
import com.betagames.dto.CategoriesDTO;
import com.betagames.request.CategoriesRequest;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.ICategoriesService;

/**
 *
 * @author Cristhian Guerrero
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
@Transactional
public class CategoriesControllerTest {

    private CategoriesRequest categoriesRequest = new CategoriesRequest();

    @Autowired
    private CategoriesController categoriesController;

    @Autowired
    private ICategoriesService categoriesService;

    @Autowired
    private Logger log;

    @BeforeEach
    public void setUp() throws Exception{
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

    @Test
    @Order(2)
    public void testListError() throws Exception {
        List<CategoriesDTO> lCategoriesDTOs = categoriesService.list();
        lCategoriesDTOs.forEach(System.out::println);
        categoriesRequest.setId(1);
        categoriesService.delete(categoriesRequest);
        ResponseList<CategoriesDTO> r = categoriesController.list();
        if (r.getData().size()==0) {
            r.setRc(false);
        }
        System.out.println(r.getMsg());
        Assertions.assertThat(r.getRc()).isEqualTo(false);
        Assertions.assertThat(r.getMsg()).isEqualTo(null);
    }
}
