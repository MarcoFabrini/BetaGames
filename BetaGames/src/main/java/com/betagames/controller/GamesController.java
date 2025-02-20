package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.GamesDTO;
import com.betagames.request.GamesRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.response.ResponseObject;
import com.betagames.service.interfaces.IGamesService;


/**
 * @author DorigoLorenzo
 **/

@RestController
@RequestMapping("/rest/")
@CrossOrigin(origins = "*")
public class GamesController {

    @Autowired
    Logger log;

    @Autowired
    IGamesService gamesService;

    @GetMapping("public/games/list")
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

    @GetMapping("public/games/searchByTyping")
    public ResponseList<GamesDTO> searchByTyping( @RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "authorsId", required = false) Integer authorsId,
                                                  @RequestParam(value = "categoriesId", required = false) Integer categoriesId,
                                                  @RequestParam(value = "editorId", required = false) Integer editorId) {
        ResponseList<GamesDTO> list = new ResponseList<>();
        list.setRc(true);
        try {
            list.setData(gamesService.searchByTyping(name, authorsId, categoriesId, editorId));
        } catch (Exception e) {
            log.error(e.getMessage());
            list.setMsg(e.getMessage());
            list.setRc(false);
        }
        return list;
    }//searchByTyping

    @GetMapping("public/games/listById")
    public ResponseObject<GamesDTO> listById(@RequestParam Integer id){
        ResponseObject<GamesDTO> r = new ResponseObject<GamesDTO>();
        r.setRc(true);
        try{
            r.setData((gamesService.listById(id)));
        } catch (Exception e) {
            log.error(e.getMessage());
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }//listById
    
    @PostMapping("admin/games/create")
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
    
    @PostMapping("admin/games/update")
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

    @PostMapping("admin/games/delete")
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
