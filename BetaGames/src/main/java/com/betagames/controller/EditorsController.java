/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.EditorsDTO;
import com.betagames.request.EditorsRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IEditorsService;

/**
 *
 * @author vattelappesca
 */

 @RestController
 @RequestMapping("/rest/editors")
public class EditorsController {
    @Autowired
    Logger log;
    @Autowired
    IEditorsService editorsService;

    @GetMapping("/list")
	public ResponseList<EditorsDTO> list(){
		ResponseList<EditorsDTO> list =  new ResponseList<EditorsDTO>();
		list.setRc(true);
		
		try {
			list.setData(editorsService.list());
		} catch (Exception e) {
			log.error(e.getMessage());
			list.setMsg(e.getMessage()); 
			list.setRc(false);
		}
		return list;
	}// list

    @PostMapping("/create")
    public ResponseBase create(@RequestBody(required = true) EditorsRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
        	editorsService.create(req);
            response.setMsg("Successfully created editor");
        } catch (Exception e) {
            log.error("Failed to create editor" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// create
}// class
