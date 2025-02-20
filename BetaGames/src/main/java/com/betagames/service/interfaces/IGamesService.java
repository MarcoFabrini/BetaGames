package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.GamesDTO;
import com.betagames.request.GamesRequest;

/**
 * @author DorigoLorenzo
 **/

public interface IGamesService {

    List<GamesDTO> searchByTyping(String name, Integer authorsId, Integer categoriesId, Integer editorId) throws Exception;

    List<GamesDTO> list() throws Exception;

    GamesDTO listById(Integer id) throws Exception;

    void create(GamesRequest req) throws Exception;

    void update(GamesRequest req) throws Exception;

    void delete(GamesRequest req) throws Exception;

}//interface
