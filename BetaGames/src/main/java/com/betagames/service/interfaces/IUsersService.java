package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.UsersDTO;
import com.betagames.request.UsersRequest;

/**
 *
 * @author FabriniMarco
 */
public interface IUsersService {
    List<UsersDTO> list() throws Exception;

    /*
    * da completare per ricercare tramite ordine
    */
    List<UsersDTO> searchByTyping(Integer id, String username, String email, Boolean active) throws Exception;

    void create(UsersRequest req) throws Exception;

    void update(UsersRequest req) throws Exception;

    void upgradeToAdmin(UsersRequest req) throws Exception;

    void delete(UsersRequest req) throws Exception;
}// class
