package com.betagames.service.interfaces;

import java.util.List;
import com.betagames.dto.RolesDTO;
import com.betagames.request.RolesRequest;

/*
 * 
 * @author Simone Checco
 */

public interface IRolesService {

    List<RolesDTO> listRoles() throws Exception;

    void create(RolesRequest req) throws Exception;

    void update(RolesRequest req) throws Exception;

    void delete(RolesRequest req) throws Exception;

    
}
