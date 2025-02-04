package com.betagames.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.AuthorsDTO;
import com.betagames.repository.IAuthorsRepository;
import com.betagames.request.AuthorsRequest;
import com.betagames.service.interfaces.IAuthorsService;

/**
 *
 * @author Cristhian Guerrero
 */
@Service
public class AuthorsImplementation implements IAuthorsService{

    @Autowired
    IAuthorsRepository authorsRepository;

    @Override
    public void create(AuthorsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

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

    @Override
    public List<AuthorsDTO> searchByTyping(Integer id, String name, String lastname, String country, String biography,
            Integer gameId) throws Exception {


            // List<Authors> listAuthors = authorsRepository.searchByFilter(id, name, lastname, country, biography, gameId);


            //  return listAuthors.stream()
            //          .map(s -> new AuthorsDTO(
            //              s.getId(), 
            //              s.getBiography(), 
            //              s.getCountry(), 
            //              s.getLastname(), 
            //              s.getName(), 
            //              s.getListGames()))

        return null;

     }


}// class
