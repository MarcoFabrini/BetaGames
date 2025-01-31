package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.EditorsDTO;
import com.betagames.request.EditorsRequest;

/**
 *
 * @author Fabrini Marco
 */
public interface IEditorsService {

    List<EditorsDTO> searchByTyping() throws Exception;

    void create(EditorsRequest req) throws Exception;

    void update(EditorsRequest req) throws Exception;

    void delete(EditorsRequest req) throws Exception;

}// interfaces
