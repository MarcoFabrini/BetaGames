package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.CategoriesDTO;
import com.betagames.request.CategoriesRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.ICategoriesService;

/**
 *
 * @author Cristhian Guerrero
 */
@RestController
@RequestMapping("/rest/")
@CrossOrigin(origins = "*")
public class CategoriesController {
   
    private final Logger log;
    private final ICategoriesService categoriesService;

    public CategoriesController(ICategoriesService categoriesService, Logger log){
        this.categoriesService=categoriesService;
        this.log=log;
    }

    @GetMapping("public/categories/list")
    public ResponseList<CategoriesDTO> list() {
        ResponseList<CategoriesDTO> listAuthors = new ResponseList<>();
        listAuthors.setRc(true);

        try {
            listAuthors.setData(categoriesService.list());
        } catch (Exception e) {
            log.error(e.getMessage());
            listAuthors.setMsg(e.getMessage());
            listAuthors.setRc(false);
        }
        return listAuthors;
    }// list

    @GetMapping("public/categories/searchByTyping")
    public ResponseList<CategoriesDTO> searchByTyping(@RequestParam(value="id", required = false) Integer id,
                                                   @RequestParam(value="name", required = false) String name) {
        ResponseList<CategoriesDTO> listCategorie = new ResponseList<>();
        listCategorie.setRc(true);

        try {
            listCategorie.setData(categoriesService.searchByTyping(id, name));
        } catch (Exception e) {
            log.error(e.getMessage());
            listCategorie.setMsg(e.getMessage());
            listCategorie.setRc(false);
        }
        return listCategorie;
    }// searchByTyping

    @PostMapping("admin/categories/create")
    public ResponseBase create(@RequestBody(required = true) CategoriesRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
                categoriesService.create(req);
                response.setMsg("Successfully created Category");   
        } catch (Exception e) {
            log.error("Failed to create Category" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// create

    @PostMapping("admin/categories/update")
    public ResponseBase update(@RequestBody(required = true) CategoriesRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            categoriesService.update(req);
            response.setMsg("Successfully updated Category");
        } catch (Exception e) {
            log.error("Failed to create Category" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// update

    @PostMapping("admin/categories/delete")
    public ResponseBase delete(@RequestBody(required = true) CategoriesRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            categoriesService.delete(req);
            response.setMsg("Successfully deleted category");
        } catch (Exception e) {
            log.error("Failed to delete category" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// delete

}// class
