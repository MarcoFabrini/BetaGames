package com.betagames.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betagames.dto.CategoriesDTO;
import com.betagames.request.CategoriesRequest;
import com.betagames.service.interfaces.ICategoriesService;

@Service
public class CategoriesImplementation implements ICategoriesService {

    @Override
    public List<CategoriesDTO> list() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'list'");
    }

    @Override
    public List<CategoriesDTO> searchByTyping(Integer id, String name, String lastname, String country,
            String biography) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByTyping'");
    }

    @Override
    public void create(CategoriesRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void update(CategoriesRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(CategoriesRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }



}
