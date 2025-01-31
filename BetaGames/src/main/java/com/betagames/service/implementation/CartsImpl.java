package com.betagames.service.implementation;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.CartsDTO;
import com.betagames.model.Carts;
import com.betagames.repository.IDetailsCartsRepository;
import com.betagames.request.CartsRequest;
import com.betagames.service.interfaces.CartsService;

@Service
public class CartsImpl implements CartsService{

    @Autowired
    IDetailsCartsRepository detailsCartR;

    @Autowired
    IUsersRepository usersR;

    @Autowired
	private Logger log;

    @Override
    public  void create(CartsRequest req) throws Exception{

        Date now = new Date();

        Optional<Users> users = 

        Carts carts = new Carts();
        carts.setUser(req.getUserId());//repository
        carts.setCreatedAt(now);
        carts.setUpdatedAt(now);

        detailsCartR.save(carts);
    }

    @Override
    public void update(CartsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void remove(CartsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public List<CartsDTO> listCartsByUser(Integer id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listCartsByUser'");
    }
    
}
