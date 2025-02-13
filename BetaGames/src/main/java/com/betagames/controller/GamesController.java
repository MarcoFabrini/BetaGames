package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.GamesDTO;
import com.betagames.request.GamesRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IGamesService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author DorigoLorenzo
 **/

@RestController
@RequestMapping("/rest/games")
@CrossOrigin(origins = "*")
public class GamesController {

    @Autowired
    Logger log;

    @Autowired
    IGamesService gamesService;

    @GetMapping("/list")
    public ResponseList<GamesDTO> list() {
        ResponseList<GamesDTO> list = new ResponseList<>();
        list.setRc(true);
        try {
            list.setData(gamesService.list());
        } catch (Exception e) {
            log.error(e.getMessage());
            list.setMsg(e.getMessage());
            list.setRc(false);
        }
        return list;
    }//list
    
    @PostMapping("/create")
    public ResponseBase create(@RequestBody(required = true) GamesRequest req) {
        ResponseBase r = new ResponseBase();        //r = response
        r.setRc(true);
        try {
            gamesService.create(req);
            r.setMsg("Game successfully CREATED!");
        } catch (Exception e) {
            log.error("Error during the creation of the game: "+e.getMessage());
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }//create
    
    @PostMapping("/update")
    public ResponseBase update(@RequestBody(required = true) GamesRequest req){
        ResponseBase r = new ResponseBase();
        r.setRc(true);
        try {
            gamesService.update(req);
            r.setMsg("Game successfully UPDATED!");
        } catch (Exception e) {
            log.error("Error during the update of the game: "+e.getMessage());
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }//update

    @PostMapping("/delete")
    public ResponseBase delete(@RequestBody(required = true) GamesRequest req){
        ResponseBase r = new ResponseBase();
        r.setRc(true);
        try {
            gamesService.delete(req);
            r.setMsg("Game successfully DELETED!");
        } catch (Exception e) {
            log.error("Error during the delete of the game: "+e.getMessage());
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }//delete

}//class
