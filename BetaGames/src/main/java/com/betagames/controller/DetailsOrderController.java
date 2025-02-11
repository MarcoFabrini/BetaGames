package com.betagames.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.DetailsOrderDTO;
import com.betagames.request.DetailsOrderRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IDetailsOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/rest/details")
public class DetailsOrderController {
    @Autowired
    IDetailsOrderService detOrderS;

    @GetMapping("/readDetailsOrder")
    public ResponseList<DetailsOrderDTO> listDetailsOrder(@RequestParam("id") Integer id) {
        ResponseList<DetailsOrderDTO> responseList = new ResponseList<DetailsOrderDTO>();
        responseList.setRc(true);
        System.out.println("Richiesta: " + id);
        try {
            responseList.setData(detOrderS.searchByOrder(id));
        } catch (Exception e) {
            responseList.setRc(false);
            responseList.setMsg(e.getMessage());
        }
        return responseList;
    }

    @PostMapping("/createDetailsOrder")
    public ResponseBase create(@RequestBody(required = true) DetailsOrderRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
            detOrderS.create(req);
        } catch (Exception e) {
            response.setRc(false);
            response.setMsg(e.getMessage());
        }

        return response;
    }

    @PostMapping("/updateDetailsOrder")
    public ResponseBase update(@RequestBody(required = true) DetailsOrderRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
            detOrderS.update(req);
        } catch (Exception e) {
            response.setRc(false);
            response.setMsg(e.getMessage());
        }

        return response;
    }
}
