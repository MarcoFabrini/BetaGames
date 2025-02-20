package com.betagames.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betagames.dto.RolesDTO;
import com.betagames.request.RolesRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IRolesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * 
 * @author Simone Checco
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/")
public class RolesController {

    @Autowired
    IRolesService rolesService;
    
    @GetMapping("admin/roles/readRoles")
    public ResponseList<RolesDTO> listRoles() {
        ResponseList<RolesDTO> responseList = new ResponseList<RolesDTO>();
        responseList.setRc(true);

        try {
            responseList.setData(rolesService.listRoles());
        } catch (Exception e) {
            responseList.setRc(false);
            responseList.setMsg(e.getMessage());
        }
        return responseList;
    }
    
    @PostMapping("admin/roles/createRole")
    public ResponseBase create(@RequestBody(required = true) RolesRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);

        try {
            rolesService.create(req);
        } catch (Exception e) {
            response.setRc(false);
            response.setMsg(e.getMessage());
        }
        
        return response;
    }
    
   @PostMapping("admin/roles/updateRole")
    public ResponseBase update(@RequestBody(required = true)RolesRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            rolesService.update(req);
        } catch (Exception e) {
            response.setRc(false);
            response.setMsg(e.getMessage());
        }
        
        return response;
    }
    
    @PostMapping("admin/roles/deleteRole")
    public ResponseBase delete(@RequestBody(required = true) RolesRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        try {
            rolesService.delete(req);
        } catch (Exception e) {
            response.setRc(false);
            response.setMsg(e.getMessage());
        }
        
        return response;
    }
    
}
