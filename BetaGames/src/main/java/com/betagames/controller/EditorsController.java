package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.EditorsDTO;
import com.betagames.request.EditorsRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IEditorsService;

/**
 *
 * @author FabriniMarco
 */

@RestController
@RequestMapping("/rest/editors")
public class EditorsController {
    @Autowired
    Logger log;
    @Autowired
    IEditorsService editorsService;

    @GetMapping("/list")
    public ResponseList<EditorsDTO> list() {
        ResponseList<EditorsDTO> list = new ResponseList<>();
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

    @GetMapping("/searchByTyping")
    public ResponseList<EditorsDTO> searchByTyping(@RequestParam(value="id", required = false) Integer id,
                                                   @RequestParam(value="name", required = false) String name,
                                                   @RequestParam(value="website", required = false) String website) {
        ResponseList<EditorsDTO> list = new ResponseList<>();
        list.setRc(true);

        try {
            list.setData(editorsService.searchByTyping(id, name, website));
        } catch (Exception e) {
            log.error(e.getMessage());
            list.setMsg(e.getMessage());
            list.setRc(false);
        }
        return list;
    }// searchByTyping

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

    @PostMapping("/update")
    public ResponseBase update(@RequestBody(required = true) EditorsRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            editorsService.update(req);
            response.setMsg("Successfully updated editor");
        } catch (Exception e) {
            log.error("Failed to update editor" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// update

    @PostMapping("/delete")
    public ResponseBase delete(@RequestBody(required = true) EditorsRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            editorsService.delete(req);
            response.setMsg("Successfully deleted editor");
        } catch (Exception e) {
            log.error("Failed to delete editor" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// delete

}// class
