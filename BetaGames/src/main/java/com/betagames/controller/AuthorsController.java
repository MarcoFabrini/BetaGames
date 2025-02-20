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

import com.betagames.dto.AuthorsDTO;
import com.betagames.request.AuthorsRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IAuthorsService;

/**
 *
 * @author Cristhian Guerrero
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/")
public class AuthorsController {
    @Autowired
    Logger log;
    @Autowired
    IAuthorsService authorsService;

    @GetMapping("public/authors/list")
    public ResponseList<AuthorsDTO> list() {
        ResponseList<AuthorsDTO> listAuthors = new ResponseList<>();
        listAuthors.setRc(true);

        try {
            listAuthors.setData(authorsService.list());
        } catch (Exception e) {
            log.error(e.getMessage());
            listAuthors.setMsg(e.getMessage());
            listAuthors.setRc(false);
        }
        return listAuthors;
    }// list

    @GetMapping("public/authors/searchByTyping")
    public ResponseList<AuthorsDTO> searchByTyping(@RequestParam(value="id", required = false) Integer id,
                                                   @RequestParam(value="name", required = false) String name,
                                                   @RequestParam(value="lastname", required = false) String lastname,
                                                   @RequestParam(value="country", required = false) String country,
                                                   @RequestParam(value="biography", required = false) String biography) {
        ResponseList<AuthorsDTO> listAuthors = new ResponseList<>();
        listAuthors.setRc(true);

        try {
            listAuthors.setData(authorsService.searchByTyping(id, name, lastname, country, biography));
        } catch (Exception e) {
            log.error(e.getMessage());
            listAuthors.setMsg(e.getMessage());
            listAuthors.setRc(false);
        }
        return listAuthors;
    }// searchByTyping

    @PostMapping("admin/authors/create")
    public ResponseBase create(@RequestBody(required = true) AuthorsRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            authorsService.create(req);
            response.setMsg("Successfully created Author");
        } catch (Exception e) {
            log.error("Failed to create Author" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// create

    @PostMapping("admin/authors/update")
    public ResponseBase update(@RequestBody(required = true) AuthorsRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            authorsService.update(req);
            response.setMsg("Successfully updated Author");
        } catch (Exception e) {
            log.error("Failed to create Author" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// update

    @PostMapping("admin/authors/delete")
    public ResponseBase delete(@RequestBody(required = true) AuthorsRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            authorsService.delete(req);
            response.setMsg("Successfully deleted author");
        } catch (Exception e) {
            log.error("Failed to delete author" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// delete

}// class
