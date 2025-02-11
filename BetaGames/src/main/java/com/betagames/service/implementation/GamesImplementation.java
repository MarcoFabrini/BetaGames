package com.betagames.service.implementation;

import static com.betagames.utility.Utilities.buildGamesDTO;
import static com.betagames.utility.Utilities.convertStringToDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

/**
 * @author DorigoLorenzo
 **/

@Service
public class GamesImplementation implements IGamesService {

    // Autowired(s)
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

    // @Override
    // public List<GamesDTO> searchByTyping() throws Exception {
    // return null;
    // }

    /*
     * Per il create di tutte i collegamenti molti a molti?
     * es. Category / Authors
     * Collegamenti con Review? DetailsCart e DetailsOrder?
     */

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
            throw new Exception("Game already present!");
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

        if (req.getAuthorsId() != null) {
            Optional<Authors> a = authorsR.findById(req.getAuthorsId());
            if (a.isPresent()) {
                Authors author = a.get();
                if (g.getListAuthors() == null) {
                    g.setListAuthors(new ArrayList<>());
                }
                g.getListAuthors().add(author);
            }
        }

        if (req.getCategoryId() != null) {
            Optional<Categories> c = categoryR.findById(req.getCategoryId());
            if (c.isPresent()) {
                Categories category = c.get();
                if (g.getListCategory() == null) {
                    g.setListCategory(new ArrayList<>());
                }
                g.getListCategory().add(category);
            }
        }

        if (req.getReviewsId() != null) {
            Optional<Reviews> r = reviewsRe.findById(req.getReviewsId());
            if (r.isPresent()) {
                Reviews review = r.get();
                if (g.getListReviews() == null) {
                    g.setListReviews(new ArrayList<>());
                }
                g.getListReviews().add(review);
            }
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
            throw new Exception("Game not found!");
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

        if (req.getReviewsId() != null) {
            Optional<Reviews> r = reviewsRe.findById(req.getReviewsId());
            if (r.isPresent()) {
                Reviews review = r.get();
                g.getListReviews().add(review);
            }
        }
        // save
        gamesR.save(g);

    }// update

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(GamesRequest req) throws Exception {
        Optional<Games> game = gamesR.findById(req.getId());
        if (!game.isPresent())
            throw new Exception("Game not found!");
        gamesR.delete(game.get());
    }// delete

}// class
