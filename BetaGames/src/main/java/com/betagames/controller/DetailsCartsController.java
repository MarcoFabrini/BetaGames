package com.betagames.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.CartsDTO;
import com.betagames.dto.DetailsCartDTO;
import com.betagames.request.DetailsCartRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IDetailsCartsService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/rest/detailsCarts")
public class DetailsCartsController {

    @Autowired
    Logger log;

    @Autowired
    IDetailsCartsService detailsCartsService;

    @PostMapping("/create")
    public ResponseBase create(@RequestBody(required = true) DetailsCartRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            detailsCartsService.create(req);
            response.setMsg("Successfully created detailsCart");
        } catch (Exception e) {
            log.error("Failed to create detailsCart" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }

    @PostMapping("/update")
    public ResponseBase update(@RequestBody(required = true) DetailsCartRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            detailsCartsService.update(req);
            response.setMsg("Successfully updated detailsCart");
        } catch (Exception e) {
            log.error("Failed to update detailsCart" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }

    @PostMapping("/delete")
    public ResponseBase delete(@RequestBody(required = true) DetailsCartRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            detailsCartsService.delete(req);
            response.setMsg("Successfully updated detailsCart");
        } catch (Exception e) {
            log.error("Failed to update detailsCart" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }

    @PostMapping("/checkout")
    public ResponseBase checkout(@RequestBody(required = true) DetailsCartRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            detailsCartsService.deleteAllByCart(req);
            response.setMsg("Successfully updated detailsCart");
        } catch (Exception e) {
            log.error("Failed to update detailsCart" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }
    
    @GetMapping("/list")
    public ResponseList<DetailsCartDTO> list() {
        ResponseList<DetailsCartDTO> response = new ResponseList<DetailsCartDTO>();
        response.setRc(true);
        try {
            response.setData(detailsCartsService.list());
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }
    @GetMapping("/listByCarts")
    public ResponseList<DetailsCartDTO> listByCarts(Integer id) {
        ResponseList<DetailsCartDTO> response = new ResponseList<DetailsCartDTO>();
        response.setRc(true);
        try {
            response.setData(detailsCartsService.listByCarts(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }
}
