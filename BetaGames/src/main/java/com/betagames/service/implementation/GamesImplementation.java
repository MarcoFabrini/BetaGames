package com.betagames.service.implementation;

import static com.betagames.utility.Utilities.buildGamesDTO;
import static com.betagames.utility.Utilities.convertStringToDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.GamesDTO;
import com.betagames.model.Authors;
import com.betagames.model.Categories;
import com.betagames.model.Editors;
import com.betagames.model.Games;
import com.betagames.model.Reviews;
import com.betagames.repository.IAuthorsRepository;
import com.betagames.repository.ICategoriesRepository;
import com.betagames.repository.IEditorsRepository;
import com.betagames.repository.IGamesRepository;
import com.betagames.repository.IReviewsRepository;
import com.betagames.request.GamesRequest;
import com.betagames.service.interfaces.IGamesService;
import com.betagames.service.interfaces.IServiceMessagesService;

/**
 * @author DorigoLorenzo
 **/

@Service
public class GamesImplementation implements IGamesService {

    // Autowired(s)

    @Autowired
    IServiceMessagesService serviceMessagesService;

    @Autowired
    IGamesRepository gamesR;

    @Autowired
    IEditorsRepository editorsR;

    @Autowired
    IAuthorsRepository authorsR;

    @Autowired
    ICategoriesRepository categoryR;

    @Autowired
    IReviewsRepository reviewsRe;

    @Autowired
    Logger log;

    @Override
    public List<GamesDTO> searchByTyping(String name, Integer authorsId, Integer categoriesId, Integer editorId) throws Exception {
        List<Games> listGames = gamesR.searchByTyping(name, authorsId, categoriesId, editorId);
        return buildGamesDTO(listGames);
    }//searchByTyping


    @Override
    public List<GamesDTO> list() throws Exception {
        List<Games> listGames = gamesR.findAll();
        return buildGamesDTO(listGames);
    }// list

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(GamesRequest req) throws Exception {
        // verifico che non esitano già giochi con lo stesso nome
        Optional<Games> game = gamesR.findByName(req.getName());
        if (game.isPresent()) {
            throw new Exception(serviceMessagesService.getMessage("game-Present"));
        }
        // recupero l'editor di riferimento
        Optional<Editors> editor = editorsR.findById(req.getEditorsId());
        // nuovo gioco e lo popolo
        Games g = new Games();
        g.setName(req.getName());
        g.setDescription(req.getDescription());
        g.setPrice(req.getPrice());
        g.setDate(convertStringToDate(req.getDate()));
        g.setMinGameTime(req.getMinGameTime());
        g.setMaxGameTime(req.getMaxGameTime());
        g.setMinPlayerNumber(req.getMinPlayerNumber());
        g.setMaxPlayerNumber(req.getMaxPlayerNumber());
        g.setMinAge(req.getMinAge());
        g.setStockQuantity(req.getStockQuantity());
        g.setEditor(editor.get());

        //inizializzo le liste
        g.setListAuthors(new ArrayList<>());
        g.setListCategory(new ArrayList<>());

        if (req.getAuthorsId() != null) {
            Optional<Authors> a = authorsR.findById(req.getAuthorsId());
            if (a.isPresent()) {
                Authors author = a.get();
                g.getListAuthors().add(author);
            }
        } else {
            g.setListAuthors(new ArrayList<>());
        }

        if (req.getCategoryId() != null) {
            Optional<Categories> c = categoryR.findById(req.getCategoryId());
            if (c.isPresent()) {
                Categories category = c.get();
                g.getListCategory().add(category);
            }
        } else {
            g.setListCategory(new ArrayList<>());
        }
        
        // save
        gamesR.save(g);
    }// create

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GamesRequest req) throws Exception {
        //
        Optional<Games> game = gamesR.findById(req.getId());
        if (!game.isPresent()) {
            throw new Exception(serviceMessagesService.getMessage("game-noPresent"));
        }
        // recupero l'editor di riferimento
        Optional<Editors> editor = editorsR.findById(req.getEditorsId());
        // nuovo gioco e lo popolo
        Games g = game.get();
        g.setName(req.getName());
        g.setDescription(req.getDescription());
        g.setPrice(req.getPrice());
        g.setDate(convertStringToDate(req.getDate()));
        g.setMinGameTime(req.getMinGameTime());
        g.setMaxGameTime(req.getMaxGameTime());
        g.setMinPlayerNumber(req.getMinPlayerNumber());
        g.setMaxPlayerNumber(req.getMaxPlayerNumber());
        g.setMinAge(req.getMinAge());
        g.setStockQuantity(req.getStockQuantity());
        g.setEditor(editor.get());

        if (req.getAuthorsId() != null) {
            Optional<Authors> a = authorsR.findById(req.getAuthorsId());
            if (a.isPresent()) {
                Authors author = a.get();
                g.getListAuthors().add(author);
            }
        }

        if (req.getCategoryId() != null) {
            Optional<Categories> c = categoryR.findById(req.getCategoryId());
            if (c.isPresent()) {
                Categories category = c.get();
                g.getListCategory().add(category);
            }
        }

        // save
        gamesR.save(g);

    }// update

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(GamesRequest req) throws Exception {
        Optional<Games> game = gamesR.findById(req.getId());
        if (!game.isPresent()) {
            throw new Exception(serviceMessagesService.getMessage("game-noPresent"));
        }

        Games gameEntity = game.get();

        if (gameEntity.getListAuthors() != null) {
            gameEntity.getListAuthors().forEach(author -> author.getListGames().remove(game));
            gameEntity.getListAuthors().clear();
        }

        if (gameEntity.getListCategory() != null) {
            gameEntity.getListCategory().forEach(category -> category.getListGames().remove(gameEntity));
            gameEntity.getListCategory().clear();
        }
        
        // if (gameEntity.getListReviews() != null) {
        //     gameEntity.getListReviews().forEach(review -> review.setGame(null));
        //     gameEntity.getListReviews().clear();
        // }
    
        gamesR.save(gameEntity);

        gamesR.delete(game.get());
    }// delete


}// class
