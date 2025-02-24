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

import com.betagames.dto.SignInDTO;
import com.betagames.dto.TokenDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.request.SignInRequest;
import com.betagames.request.UsersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.response.ResponseObject;
import com.betagames.service.interfaces.IUsersService;

/**
 *
 * @author FabriniMarco
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/")
public class UsersController {
    @Autowired
    Logger log;
    @Autowired
    IUsersService usersService;

    @GetMapping("admin/users/list")
    public ResponseList<UsersDTO> list() {
        log.debug("msg");
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

    @PostMapping("public/users/createUser")
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

    // @PostMapping("public/users/signin")
    // public SignInDTO signin(@RequestBody(required = true) SignInRequest req) {
    //     return usersService.signIn(req);
    // }//signIn

    @PostMapping("public/user/updatePWD")
	public ResponseBase updatePWD(@RequestBody (required = true) SignInRequest req) {
		log.debug("updatePWD :" + req);
		ResponseBase r = new ResponseBase();
		r.setRc(true);
		try {
			usersService.changePWD(req);
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			r.setRc(false);
		}
		return r;
	}//changePwd

    @PostMapping("public/users/signin")
    public ResponseBase signin(@RequestBody(required = true) UsersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
            usersService.signin(req);
            response.setMsg("Successfully created user");
        } catch (Exception e) {
            log.error("Failed to create user " + e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }
        return response;
    }// signin

    @PostMapping("public/users/login")
    public ResponseObject<SignInDTO> login(@RequestBody(required = true) UsersRequest req) throws Exception {
        ResponseObject<SignInDTO> response = new ResponseObject<>();
        response.setRc(true);

        try {
            response.setData(usersService.login(req));
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setMsg(e.getMessage());
            response.setRc(false);
        }

        // Restituisce l'oggetto ResponseObject come risposta
        return response;
    } // login

}// class
