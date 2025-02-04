package com.betagames.service.implementation;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.AuthorsDTO;
import com.betagames.model.Authors;
import com.betagames.repository.IAuthorsRepository;
import com.betagames.request.AuthorsRequest;
import com.betagames.service.interfaces.IAuthorsService;
import static com.betagames.utility.Utilities.buildAuthorsDTO;

/**
 *
 * @author Cristhian Guerrero
 */
@Service
public class AuthorsImplementation implements IAuthorsService{

    private IAuthorsRepository authorsRepository;
    private Logger log;

    @Autowired
    public AuthorsImplementation(IAuthorsRepository authorsRepository, Logger log){
        this.authorsRepository = authorsRepository;
        this.log = log;
    }
    
    // @Override
    // public List<AuthorsDTO> searchByTyping(Integer id, String name, String lastname, String country, String biography,
    //         Integer gameId) throws Exception {
    //         log.debug("Cercando lista di autori con filtri");
    //         List<Authors> listAuthors = authorsRepository.searchByFilter(id, name, lastname, country, biography, gameId);
    //         log.debug("List SearchByTyping: " + listAuthors);
    //         return buildAuthorsDTO(listAuthors);
    // }//List Search

    @Override
    public List<AuthorsDTO> list() throws Exception {
        log.debug("Cercando lista di autori");
        List<Authors> listAuthors = authorsRepository.findAll();
        log.debug("List SearchByTyping: " + listAuthors);
        return buildAuthorsDTO(listAuthors);
    }//List

    @Override
    public void create(AuthorsRequest req) throws Exception {
        Optional<Authors> author = authorsRepository.findByNameAndLastname(req.getName(), req.getLastname());
        if (author.isPresent())
            throw new Exception("This authors name and lasname is present");
        // author = authorsRepository.findByGameId(req.getGameId());
        // if (author.isPresent())
        //     throw new Exception("This authors id Games is already present");
        
        Authors a = new Authors();
        a.setName(req.getName());
        a.setLastname(req.getLastname());
        a.setCountry(req.getCountry());
        a.setBiography(req.getBiography());
        a.setListGames(null);//null da cambiare quando ho i metodi di games

        authorsRepository.save(a);
    }//create

    @Override
    public void update(AuthorsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(AuthorsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}// class
