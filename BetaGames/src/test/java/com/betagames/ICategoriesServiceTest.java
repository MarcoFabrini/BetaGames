package com.betagames;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.CategoriesDTO;
import com.betagames.model.Categories;
import com.betagames.model.Games;
import com.betagames.repository.IGamesRepository;
import com.betagames.request.CategoriesRequest;
import com.betagames.service.interfaces.ICategoriesService;

@SpringBootTest
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
    void testCreate() throws Exception {
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


        try { 
           gamesRepository.save(games);
            Optional<Games> g = gamesRepository.findById(1);
            log.debug("GamesOptional: " + g);
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        req.setName("Terror");
        req.setGamesId(2);
        req2.setName("Pokemon");

        categoriesService.create(req);
        categoriesService.create(req2);

        List<CategoriesDTO> lC = categoriesService.list();

        CategoriesDTO c1 = lC.stream().filter(c -> "Terror".equals(c.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));
        Assertions.assertThat(c1.getId()).isEqualTo(1);
        lC.forEach(s -> log.debug(s.toString()));

        CategoriesDTO c2 = lC.stream().filter(c -> "Pokemon".equals(c.getName()))
                .findFirst().orElseThrow(() -> new AssertionError("Category don't find"));
        Assertions.assertThat(c2.getId()).isEqualTo(2);
        lC.forEach(s -> log.debug(s.toString()));
    }

    @Test
    @Order(2)
    void testCreateError() throws Exception {
        CategoriesRequest req = new CategoriesRequest();
        req.setId(1);
        req.setName("Terror");

        categoriesService.create(req);

        assertThrows(Exception.class,  ()->categoriesService.create(req));

    }
}
