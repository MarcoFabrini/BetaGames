package com.betagames;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.CategoriesDTO;
import com.betagames.model.Games;
import com.betagames.repository.IGamesRepository;
import com.betagames.request.CategoriesRequest;
import com.betagames.service.interfaces.ICategoriesService;

/**
 *
 * @author Cristhian Guerrero
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
public class ICategoriesServiceTest {

    @Autowired
    private ICategoriesService categoriesService;
    @Autowired
    private Logger log;
    @Autowired
    private IGamesRepository gamesRepository;

    @Test
    @Order(1)
    void testCreateWithGame() throws Exception {
        CategoriesRequest req = new CategoriesRequest();
        CategoriesRequest req2 = new CategoriesRequest();
        Games games = new Games();
        games.setDate(new Date());
        games.setDescription("Hahahahah");
        games.setMaxGameTime(50);
        games.setMaxPlayerNumber(5);
        games.setMinAge(12);
        games.setMinGameTime(20);
        games.setMinPlayerNumber(2);
        games.setName("Fan");
        games.setPrice(200.0);
        games.setStockQuantity(20);

        gamesRepository.save(games);

        try {
            Optional<Games> g = gamesRepository.findById(1);
            log.debug("GamesOptional: " + g.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        req.setName("Terror");
        req.setGamesId(1);
        req2.setName("Pokemon");

        categoriesService.create(req);
        categoriesService.create(req2);

        List<CategoriesDTO> lC = categoriesService.list();

        CategoriesDTO c1 = lC.stream().filter(c -> "Terror".equals(c.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));
        Assertions.assertThat(c1.getId()).isEqualTo(1);
        lC.forEach(s -> log.debug(s.toString()));

        Assertions.assertThat(lC.size()).isEqualTo(2);
    }

    @Test
    @Order(2)
    void testCreateWithouthGame() throws Exception {
        CategoriesRequest req = new CategoriesRequest();
        CategoriesRequest req2 = new CategoriesRequest();

        req.setName("Terror");
        req.setGamesId(1);
        req2.setName("Pokemon");

        categoriesService.create(req);
        categoriesService.create(req2);

        List<CategoriesDTO> lC = categoriesService.list();

        CategoriesDTO c1 = lC.stream().filter(c -> "Terror".equals(c.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));
        Assertions.assertThat(c1.getId()).isEqualTo(1);
        lC.forEach(s -> log.debug(s.toString()));

        Assertions.assertThat(lC.size()).isEqualTo(2);
    }

    @Test
    @Order(3)
    void testCreateError() throws Exception {
        CategoriesRequest req = new CategoriesRequest();
        req.setName("Terror");

        CategoriesRequest req2 = new CategoriesRequest();
        req2.setName("Terror");

        categoriesService.create(req);
        assertThrows(Exception.class, () -> categoriesService.create(req2));
    }

    @Test
    @Order(4)
    void testUpdate() throws Exception {
        CategoriesRequest req = new CategoriesRequest();
        req.setName("Prova");
    
        categoriesService.create(req);
    
        List<CategoriesDTO> lC = categoriesService.searchByTyping(null, null);
        CategoriesDTO createdCategory = lC.stream()
                .filter(elemento -> "Prova".equals(elemento.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Created category not found"));
    
        req.setId(createdCategory.getId());
        req.setName("PokUpdate");
    
        categoriesService.update(req);
    
        List<CategoriesDTO> updatedList = categoriesService.searchByTyping(null, null);
        CategoriesDTO updatedCategory = updatedList.stream()
                .filter(elemento -> "PokUpdate".equals(elemento.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Updated category not found"));
    
        Assertions.assertThat(updatedCategory.getName()).isEqualTo("PokUpdate");
    }

    @Test
    @Order(5)
    void testUpdateError() throws Exception {
        CategoriesRequest req = new CategoriesRequest();
        req.setName("Prova");

        categoriesService.create(req);

        req.setId(4);
        req.setName("PokUpdate");

        assertThrows(Exception.class, () -> categoriesService.update(req));
    }

    @Test
    @Order(6)
    void testUpdateErrorNameIsEmpty() throws Exception {
        CategoriesRequest req = new CategoriesRequest();
        req.setName("Prova");

        categoriesService.create(req);

        req.setId(1);
        req.setName("");
        assertThrows(Exception.class, () -> categoriesService.update(req));
    }

    @Test
    @Order(7)
    void testUpdateErrorNameIsNull() throws Exception {
        CategoriesRequest req = new CategoriesRequest();
        req.setName("Prova");

        categoriesService.create(req);

        req.setId(1);
        req.setName(null);
        assertThrows(Exception.class, () -> categoriesService.update(req));
    }

    @Test
    @Order(8)
    void testDelete() throws Exception {
        CategoriesRequest req = new CategoriesRequest();
        CategoriesRequest req2 = new CategoriesRequest();

        req.setName("Terror");
        req.setGamesId(1);
        req2.setName("Pokemon");

        categoriesService.create(req);
        categoriesService.create(req2);

        List<CategoriesDTO> lC = categoriesService.list();

        CategoriesDTO c1 = lC.stream().filter(c -> "Terror".equals(c.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));

    }

}
