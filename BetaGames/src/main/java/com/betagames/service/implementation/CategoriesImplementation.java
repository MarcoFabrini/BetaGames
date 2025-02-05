package com.betagames.service.implementation;

import static com.betagames.utility.Utilities.buildCategoriesDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.CategoriesDTO;
import com.betagames.model.Categories;
import com.betagames.model.Games;
import com.betagames.repository.ICategoriesRepository;
import com.betagames.repository.IGamesRepository;
import com.betagames.request.CategoriesRequest;
import com.betagames.service.interfaces.ICategoriesService;

/**
 *
 * @author Cristhian Guerrero
 */
@Service
public class CategoriesImplementation implements ICategoriesService {

    private ICategoriesRepository categoriesRepository;
    private IGamesRepository gamesRepository;
    private Logger log;

    public CategoriesImplementation(ICategoriesRepository categoriesRepository, Logger log,
            IGamesRepository gamesRepository) {
        this.categoriesRepository = categoriesRepository;
        this.log = log;
    }

    @Override
    public List<CategoriesDTO> list() throws Exception {
        List<Categories> listCategories = categoriesRepository.findAll();
        return buildCategoriesDTO(listCategories);
    }// List

    @Override
    public List<CategoriesDTO> searchByTyping(Integer id, String name) throws Exception {
        List<Categories> listCategories = categoriesRepository.searchByFilter(id, name);
        return buildCategoriesDTO(listCategories);
    }// SearchByTyping

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CategoriesRequest req) throws Exception {
        Optional<Categories> categoria = categoriesRepository.findByName(req.getName());
        if (categoria.isPresent()) {
            throw new Exception("The Categoiria is present in DB");
        }

        Categories c = new Categories();
        c.setName(req.getName());
        if (req.getGamesId() != null) {
            Optional<Games> g = gamesRepository.findById(req.getGamesId());
            if (g.isPresent()) {
                Games game = g.get();
                if (c.getListGames() == null) {
                    c.setListGames(new ArrayList<>());
                }
                c.getListGames().add(game);
            }
        }
        try {
            categoriesRepository.save(c);
            log.debug("The category has been successfully added.");
        } catch (Exception e) {
            log.debug("There was an issue while adding the category.");
        }

    }// Create

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CategoriesRequest req) throws Exception {
        Objects.requireNonNull(req.getId(), "The category's id isn't present");
        Optional<Categories> categoria = categoriesRepository.findById(req.getId());
        if (!categoria.isPresent()) {
            throw new Exception("The Category isn't present in DB");
        }

        Categories c = categoria.get();
        if (req.getName() != null) {
            c.setName(req.getName());
        }
        //Qua gestisco anche la modifica di i giocchi del db, ma non so si deviamo farlo direttamente nella entita Games
/*         if (req.getGamesId() != null) {
            Optional<Games> g = gamesRepository.findById(req.getGamesId());
            if (g.isPresent()) {
                Games game = g.get();
                c.getListGames().clear();
                if (c.getListGames() == null) {
                    c.setListGames(new ArrayList<>());
                }
                c.getListGames().add(game);
            }
        } */
        try {
            categoriesRepository.save(c);
            log.debug("The category has been successfully updated.");
        } catch (Exception e) {
            log.error("There was an issue while updating the category: " + e.getMessage());
        }
    }// Update

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(CategoriesRequest req) throws Exception {
        Objects.requireNonNull(req.getId(), "The category's id isn't present.");
        Optional<Categories> categoria = categoriesRepository.findById(req.getId());

        if (!categoria.isPresent()) {
            throw new Exception("The category isn't present");
        }

        Categories c = categoria.get();
        if(c.getListGames() != null){
            c.getListGames().forEach(game->game.getListCategory().remove(c));
            c.getListGames().clear();
        }

        try {
            categoriesRepository.delete(c);
            log.debug("The category has been successfully deleted.");

        } catch (Exception e) {
            log.error("There was an issue while deleting the category: " + e.getMessage());
        }

    }// Delete

}//class
