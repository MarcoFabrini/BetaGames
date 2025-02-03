package com.betagames.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betagames.dto.DetailsOrderDTO;
import com.betagames.request.DetailsOrderRequest;
import com.betagames.service.interfaces.IDetailsOrderService;

/*
 * 
 * @author Simone Checco
 */

 @Service
public class DetailsOrderImplmentation implements IDetailsOrderService{

    @Override
    public List<DetailsOrderDTO> searchByTyping() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByTyping'");
    }

    @Override
    public void create(DetailsOrderRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void update(DetailsOrderRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(DetailsOrderRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
