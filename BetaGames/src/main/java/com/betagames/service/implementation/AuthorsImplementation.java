package com.betagames.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.AuthorsDTO;
import com.betagames.model.Authors;
import com.betagames.model.Games;
import com.betagames.repository.IAuthorsRepository;
import com.betagames.repository.IGamesRepository;
import com.betagames.request.AuthorsRequest;
import com.betagames.service.interfaces.IAuthorsService;

import static com.betagames.utility.Utilities.buildAuthorsDTO;

/**
 *
 * @author Cristhian Guerrero
 */
@Service
public class AuthorsImplementation implements IAuthorsService {

    private IAuthorsRepository authorsRepository;
    private IGamesRepository gamesRepository;
    private Logger log;

    public AuthorsImplementation(IAuthorsRepository authorsRepository, Logger log, IGamesRepository gamesRepository) {
        this.authorsRepository = authorsRepository;
        this.gamesRepository = gamesRepository;
        this.log = log;
    }

    @Override
    public List<AuthorsDTO> searchByTyping(Integer id, String name, String lastname, String country, String biography)
            throws Exception {
        log.debug("Cercando lista di autori con filtri");
        List<Authors> listAuthors = authorsRepository.searchByFilter(id, name,
                lastname, country, biography);
        log.debug("List SearchByTyping: " + listAuthors);
        return buildAuthorsDTO(listAuthors);
    }// List Search

    @Override
    public List<AuthorsDTO> list() throws Exception {
        log.debug("Cercando lista di autori");
        List<Authors> listAuthors = authorsRepository.findAll();
        log.debug("List SearchByTyping: " + listAuthors);
        return buildAuthorsDTO(listAuthors);
    }// List

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(AuthorsRequest req) throws Exception {
        Optional<Authors> author = authorsRepository.findByNameAndLastname(req.getName(), req.getLastname());
        if (author.isPresent())
            throw new Exception("This authors name and lastname is present");

        Objects.requireNonNull(req.getName(), "The author's name is not present.");
        Objects.requireNonNull(req.getLastname(), "The author's lastname is not present.");
        Objects.requireNonNull(req.getBiography(), "The author's biography is not present.");
        Objects.requireNonNull(req.getCountry(), "The author's country is not present.");

        Authors a = new Authors();
        a.setName(req.getName());
        a.setLastname(req.getLastname());
        a.setCountry(req.getCountry());
        a.setBiography(req.getBiography());

        if (req.getGameId() != null) {
            Optional<Games> g = gamesRepository.findById(req.getGameId());
            if (g.isPresent()) {
                Games game = g.get();
                if (a.getListGames() == null) {
                    a.setListGames(new ArrayList<>());
                }
                a.getListGames().add(game);
            }
        }
        try {
            authorsRepository.save(a);
            log.debug("The author has been successfully added.");
        } catch (Exception e) {
            log.debug("There was an issue while adding the author.");
        }
    }// create

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AuthorsRequest req) throws Exception {
        Objects.requireNonNull(req.getName(), "The author's name is not present.");
        Objects.requireNonNull(req.getLastname(), "The author's lastname is not present.");
        Optional<Authors> author = authorsRepository.findByNameAndLastname(req.getName(), req.getLastname());

        if (!author.isPresent())
            throw new Exception("This authors name or lastname isn't present");

        Authors a = author.get();

        if (req.getBiography() != null) {
            a.setBiography(req.getBiography());
        }
        if (req.getCountry() != null) {
            a.setCountry(req.getCountry());
        }

        if (req.getGameId() != null) {
            Optional<Games> g = gamesRepository.findById(req.getGameId());
            if (g.isPresent()) {
                Games game = g.get();
                if (a.getListGames() == null) {
                    a.setListGames(new ArrayList<>());
                }
                a.getListGames().add(game);
            }
        }

        authorsRepository.save(a);
    }// update

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(AuthorsRequest req) throws Exception {
        Objects.requireNonNull(req.getId(), "The author's id isn't present");
        Optional<Authors> author = authorsRepository.findById(req.getId());

        if (!author.isPresent()) {
            throw new Exception("The author isn't present");
        }

        Authors a = author.get();

        if (a.getListGames() != null) {
            a.getListGames().forEach(game -> {
                if (game.getListAuthors() != null) { // ✅ Verifica que no sea null antes de eliminar
                    game.getListAuthors().remove(a);
                }
            });
            a.getListGames().clear();
        }

        authorsRepository.delete(a);
    }

}// class
