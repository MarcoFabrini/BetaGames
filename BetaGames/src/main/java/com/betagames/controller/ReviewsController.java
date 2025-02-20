/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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

import com.betagames.dto.ReviewsDTO;
import com.betagames.request.ReviewsRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IReviewsService;

/**
 *
 * @author FabriniMarco
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/")
public class ReviewsController {
    @Autowired
    Logger log;
    @Autowired
    IReviewsService reviewsService;

    @GetMapping("admin/reviews/listByUserId")
    public ResponseList<ReviewsDTO> listByUserId(@RequestParam(value = "id", required = true) Integer id) {
        ResponseList<ReviewsDTO> list = new ResponseList<>();
        list.setRc(true);

        try {
            list.setData(reviewsService.listByUserId(id));
        } catch (Exception e) {
            log.error(e.getMessage());
            list.setMsg(e.getMessage());
            list.setRc(false);
        }
        return list;
    }// listByUserId

    @PostMapping("user/reviews/create")
    public ResponseBase create(@RequestBody(required = true) ReviewsRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
           reviewsService.create(req);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// create

    @PostMapping("user/reviews/update")
    public ResponseBase update(@RequestBody(required = true) ReviewsRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
           reviewsService.update(req);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// update

    @PostMapping("user/reviews/delete")
    public ResponseBase delete(@RequestBody(required = true) ReviewsRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
           reviewsService.delete(req);
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// delete

}// class
