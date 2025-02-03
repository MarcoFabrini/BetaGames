package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.GamesDTO;
import com.betagames.request.GamesRequest;

/**
 * @author DorigoLorenzo
 **/

public interface IGamesService {

    // List<GamesDTO> searchByTyping() throws Exception;

    void create(GamesRequest req) throws Exception;

    void update(GamesRequest req) throws Exception;

    void delete(GamesRequest req) throws Exception;

}//interface
