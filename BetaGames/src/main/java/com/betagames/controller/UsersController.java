package com.betagames.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.betagames.dto.UsersDTO;
import com.betagames.model.Users;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.response.ResponseObject;
import com.betagames.service.interfaces.IUsersService;

/**
 *
 * @author FabriniMarco
 */
@RestController
@RequestMapping("/rest/")
public class UsersController {
    @Autowired
    Logger log;
    @Autowired
    IUsersService usersService;

    @GetMapping("user/users/me")
    public ResponseObject<UserDetails> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Controlla se l'utente è autenticato
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User  is not authenticated");
        }

        // Assicurati che il principal sia del tipo corretto
        UserDetails currentUser;
        if (authentication.getPrincipal() instanceof Users) {
            currentUser = (UserDetails) authentication.getPrincipal();
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Principal is not of type Users");
        }

        // Crea e restituisci la risposta
        ResponseObject<UserDetails> responseMe = new ResponseObject<>();
        responseMe.setData(currentUser);

        return responseMe;
    }// authenticatedUser

    @GetMapping("admin/users/list")
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

    @GetMapping("admin/users/searchByTyping")
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

    @PostMapping("user/users/update")
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

    @PostMapping("admin/users/upgradeToAdmin")
    public ResponseBase upgradeToAdmin(@RequestBody(required = true) UsersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            usersService.upgradeToAdmin(req);
            response.setMsg("Successfully upgraded user to admin");
        } catch (Exception e) {
            log.error("Failed to upgraded user to admin " + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// upgradeToAdmin

    @PostMapping("user/users/delete")
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
