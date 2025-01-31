package com.betagames.service.implementation;

import java.util.List;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.betagames.dto.GamesDTO;
import com.betagames.repository.IEditorsRepository;
import com.betagames.repository.IGamesRepository;
import com.betagames.request.GamesRequest;
import com.betagames.service.interfaces.IGamesService;

/**
 * @author DorigoLorenzo
 **/

public class GamesImplementation implements IGamesService{

    //Autowired(s)
    @Autowired
    IGamesRepository gamesR;

    @Autowired
    IEditorsRepository editorsR;

    @Autowired
    Logger log;

    @Override
    public List<GamesDTO> searchByTyping() throws Exception {
        return null;
    }

    @Override
    public void create(GamesRequest req) throws Exception {
        //
    }

    @Override
    public void update(GamesRequest req) throws Exception {
        //
    }

    @Override
    public void delete(GamesRequest req) throws Exception {
        //
    }

}//class
