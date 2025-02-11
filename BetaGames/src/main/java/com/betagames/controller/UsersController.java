package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
 * @author FabriniMarco
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
                                                 @RequestParam(value = "email", required = false) String email,
                                                 @RequestParam(value = "active", required = false) Boolean active) {
        ResponseList<UsersDTO> list = new ResponseList<>();
        list.setRc(true);

        try {
            list.setData(usersService.searchByTyping(id, username, email, active));
        } catch (Exception e) {
            log.error(e.getMessage());
            list.setMsg(e.getMessage());
            list.setRc(false);
        }
        return list;
    }// searchByTyping

    @PostMapping("/createUser")
    public ResponseBase createUser(@RequestBody(required = true) UsersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
            usersService.createUser(req);
            response.setMsg("Successfully created user");
        } catch (Exception e) {
            log.error("Failed to create user " + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// createUser

    @PostMapping("/createAdmin")
    public ResponseBase createAdmin(@RequestBody(required = true) UsersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
            usersService.createAdmin(req);
            response.setMsg("Successfully created admin");
        } catch (Exception e) {
            log.error("Failed to create admin " + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// createAdmin

    @PostMapping("/login")
    public ResponseBase login(@RequestBody(required = true) UsersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
            usersService.login(req);
            response.setMsg("Successfully login");
        } catch (Exception e) {
            log.error("Failed to login " + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// login

    @PostMapping("/update")
    public ResponseBase update(@RequestBody(required = true) UsersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            usersService.update(req);
            response.setMsg("Successfully updated user");
        } catch (Exception e) {
            log.error("Failed to update user " + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// update

    @PostMapping("/delete")
    public ResponseBase delete(@RequestBody(required = true) UsersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            usersService.delete(req);
            response.setMsg("Successfully deleted user");
        } catch (Exception e) {
            log.error("Failed to delete user " + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// delete

}// class
