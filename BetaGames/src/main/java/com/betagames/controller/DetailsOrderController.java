package com.betagames.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.DetailsOrderDTO;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IDetailsOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/rest/")
public class DetailsOrderController {
    @Autowired
    IDetailsOrderService detOrderS;

    @GetMapping("user/details/readDetailsOrder")
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
}
