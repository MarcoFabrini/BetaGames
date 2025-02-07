package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.request.CartsRequest;
import com.betagames.response.ResponseBase;
import com.betagames.service.interfaces.ICartsService;

@RestController
@RequestMapping("/rest/carts")
public class CartsController {

     @Autowired
    Logger log;

    @Autowired
    ICartsService cartsService;

    @PostMapping("/create")
    public ResponseBase create(@RequestBody(required = true) CartsRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            cartsService.create(req);
            response.setMsg("Successfully created Cart");
        } catch (Exception e) {
            log.error("Failed to create Cart: " + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }

    @DeleteMapping("/delete")
    public ResponseBase delete(@RequestBody(required = true) CartsRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            cartsService.delete(req);
            response.setMsg("Successfully deleted Cart");
        } catch (Exception e) {
            log.error("Failed to delete Cart: " + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }
}
