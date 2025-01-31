package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.AuthorsDTO;
import com.betagames.request.AuthorsRequest;

/**
 *
 * @author Cristhian Guerrero
 */
public interface IAuthorsService {

    List<AuthorsDTO> searchByTyping(Integer id, String name, String lastname, String country, String biography, Integer gameId) throws Exception;

    void create(AuthorsRequest req) throws Exception;

    void update(AuthorsRequest req) throws Exception;

    void delete(AuthorsRequest req) throws Exception;

}// interfaces
