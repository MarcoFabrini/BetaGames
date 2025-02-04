package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.UsersDTO;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IUsersService;

/**
 *
 * @author Fabrini Marco
 */

@RestController
@RequestMapping("/rest/users")
public class UsersController {
    @Autowired
    Logger log;
    @Autowired
    IUsersService usersService;

    @GetMapping("/list")
    public ResponseList<UsersDTO> list() {
        ResponseList<UsersDTO> list = new ResponseList<>();
        list.setRc(true);

        try {
            list.setData(usersService.list());
        } catch (Exception e) {
            log.error(e.getMessage());
            list.setMsg(e.getMessage());
            list.setRc(false);
        }
        return list;
    }// list

    @GetMapping("/searchByTyping")
    public ResponseList<UsersDTO> searchByTyping(@RequestParam(value = "id", required = false) Integer id,
                                                 @RequestParam(value = "username", required = false) String username,
                                                 @RequestParam(value = "email", required = false) String email) {
        ResponseList<UsersDTO> list = new ResponseList<>();
        list.setRc(true);

        try {
            list.setData(usersService.searchByTyping(id, username, email));
        } catch (Exception e) {
            log.error(e.getMessage());
            list.setMsg(e.getMessage());
            list.setRc(false);
        }
        return list;
    }// searchByTyping

    @PostMapping("/create")
    public ResponseBase create(@RequestBody(required = true) UsersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
            usersService.create(req);
            response.setMsg("Successfully created user");
        } catch (Exception e) {
            log.error("Failed to create user" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// create

    @PutMapping("/update")
    public ResponseBase update(@RequestBody(required = true) UsersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            usersService.update(req);
            response.setMsg("Successfully updated user");
        } catch (Exception e) {
            log.error("Failed to update user" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// update

    @DeleteMapping("/delete")
    public ResponseBase delete(@RequestBody(required = true) UsersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            usersService.delete(req);
            response.setMsg("Successfully deleted user");
        } catch (Exception e) {
            log.error("Failed to delete user" + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// delete

}// class
