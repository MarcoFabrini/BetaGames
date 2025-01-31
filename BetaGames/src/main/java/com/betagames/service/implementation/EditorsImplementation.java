package com.betagames.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betagames.dto.EditorsDTO;
import com.betagames.request.EditorsRequest;
import com.betagames.service.interfaces.IEditorsService;

/**
 *
 * @author Fabrini Marco
 */
@Service
public class EditorsImplementation implements IEditorsService{

    @Override
    public List<EditorsDTO> searchByTyping() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByTyping'");
    }// searchByTyping

    @Override
    public void create(EditorsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }// create

    @Override
    public void update(EditorsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }// update

    @Override
    public void delete(EditorsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }// delete

}// class
