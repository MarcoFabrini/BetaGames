package com.betagames.service.interfaces;

import com.betagames.dto.TokenDTO;
import com.betagames.request.UsersRequest;

/**
 *
 * @author vattelappesca
 */
public interface IAuthService {

    TokenDTO login(UsersRequest req) throws Exception;

    void signin(UsersRequest req) throws Exception;
}// class
