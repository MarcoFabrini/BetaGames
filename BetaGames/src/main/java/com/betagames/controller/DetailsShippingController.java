package com.betagames.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.DetailsShippingDTO;
import com.betagames.request.DetailsShippingRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IDetailsShippingService;

@RestController
@RequestMapping("/rest/detailsShipping")
public class DetailsShippingController {
	
	@Autowired
	IDetailsShippingService detailsShippingService;
	
    @GetMapping("/list")
    public ResponseList<DetailsShippingDTO> list(@RequestBody(required = true) DetailsShippingRequest req) {
        ResponseList<DetailsShippingDTO> responseList = new ResponseList<>();
        responseList.setRc(true);
        try {
            responseList.setData(detailsShippingService.list(req));
        } catch (Exception e) {
            responseList.setRc(false);
            responseList.setMsg(e.getMessage());
        }
        return responseList;
    }// list
    
    @PostMapping("/create")
    public ResponseBase create(@RequestBody(required = true) DetailsShippingRequest req) {
    	ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            detailsShippingService.create(req);
        } catch (Exception e) {
            response.setRc(false);
            response.setMsg(e.getMessage());
        }
        return response;
    }// create
    
    @PostMapping("/update")
    public ResponseBase update(@RequestBody(required = true) DetailsShippingRequest req) {
    	ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            detailsShippingService.update(req);
        } catch (Exception e) {
            response.setRc(false);
            response.setMsg(e.getMessage());
        }
        return response;
    }// update
    
    @PostMapping("/delete")
    public ResponseBase delete(@RequestBody(required = true) DetailsShippingRequest req) {
    	ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            detailsShippingService.delete(req);
        } catch (Exception e) {
            response.setRc(false);
            response.setMsg(e.getMessage());
        }
        return response;
    }// delete

}// class
