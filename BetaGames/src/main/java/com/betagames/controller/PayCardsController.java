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

import com.betagames.dto.PayCardsDTO;
import com.betagames.request.PayCardsRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IPayCardsService;



/**
 * @author DorigoLorenzo
 **/
@CrossOrigin(origins = "*")
 @RestController
 @RequestMapping("/rest/")
public class PayCardsController {

    @Autowired
    Logger log;

    @Autowired
    IPayCardsService payCardsService;

    @GetMapping("admin/paycards/list")
    public ResponseList<PayCardsDTO> list() {
        ResponseList<PayCardsDTO> list = new ResponseList<>();
        list.setRc(true);
        try {
            list.setData(payCardsService.list());
        } catch (Exception e) {
            log.error(e.getMessage());
            list.setMsg(e.getMessage());
            list.setRc(false);
        }
        return list;
    }//list

    @GetMapping("user/paycards/listByUser")
    public ResponseList<PayCardsDTO> listByUser(@RequestParam Integer id) {
        ResponseList<PayCardsDTO> list = new ResponseList<>();
        list.setRc(true);
        try {
            list.setData(payCardsService.listByUser(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            list.setMsg(e.getMessage());
            list.setRc(false);
        }
        return list;
    }
    
    
    @PostMapping("user/paycards/create")
    public ResponseBase create(@RequestBody(required = true) PayCardsRequest req){
        ResponseBase r = new ResponseBase();        //r = response
        r.setRc(true);
        try {
            payCardsService.create(req);
            r.setMsg("Card successfully CREATED");
        } catch (Exception e) {
            log.error("Error during the creation of the Card: " + e.getMessage());
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }//create


    @PostMapping("user/paycards/update")
    public ResponseBase update(@RequestBody(required = true) PayCardsRequest req){
        ResponseBase r = new ResponseBase();
        r.setRc(true);
        try {
            payCardsService.update(req);
            r.setMsg("Card successfully UPDATED");
        } catch (Exception e) {
            log.error("Error during the update of the Card: "+e.getMessage());
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }//update


    @PostMapping("user/paycards/delete")
    public ResponseBase delete(@RequestBody(required = true) PayCardsRequest req){
        ResponseBase r = new ResponseBase();
        r.setRc(true);
        try {
            payCardsService.delete(req);
            r.setMsg("Card successfully DELETED");
        } catch (Exception e) {
            log.error("Error during the delete of the Card: "+e.getMessage());
            r.setMsg(e.getMessage());
            r.setRc(false);
        }
        return r;
    }//delete

}//class
