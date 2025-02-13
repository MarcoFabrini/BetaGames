package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.CartsDTO;
import com.betagames.request.CartsRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.ICartsService;

@RestController
@RequestMapping("/rest/carts")
public class CartsController {

     @Autowired
    Logger log;

    @Autowired
    ICartsService cartsService;

    // @PostMapping("/create")
    // public ResponseBase create(@RequestBody(required = true) CartsRequest req) {
    //     ResponseBase response = new ResponseBase();
    //     response.setRc(true);
    //     try {
    //         cartsService.create(req);
    //         response.setMsg("Successfully created Cart");
    //     } catch (Exception e) {
    //         log.error("Failed to create Cart: " + e.getMessage());
    //         response.setMsg(e.getMessage());
    //         response.setRc(false);
    //     }
    //     return response;
    // }

    // @PostMapping("/delete")
    // public ResponseBase delete(@RequestBody(required = true) CartsRequest req) {
    //     ResponseBase response = new ResponseBase();
    //     response.setRc(true);
    //     try {
    //         cartsService.delete(req);
    //         response.setMsg("Successfully deleted Cart");
    //     } catch (Exception e) {
    //         log.error("Failed to delete Cart: " + e.getMessage());
    //         response.setMsg(e.getMessage());
    //         response.setRc(false);
    //     }
    //     return response;
    // }

    @GetMapping("/list")
    public ResponseList<CartsDTO> list() {
        ResponseList<CartsDTO> response = new ResponseList<CartsDTO>();
        response.setRc(true);
        try {
            response.setData(cartsService.list());
        } catch (Exception e) {
            log.error("Failed to list Cart: " + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }
}
