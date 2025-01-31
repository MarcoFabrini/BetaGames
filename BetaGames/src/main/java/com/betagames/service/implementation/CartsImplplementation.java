package com.betagames.service.implementation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.CartsDTO;
import com.betagames.model.Carts;
import com.betagames.model.Users;
import com.betagames.repository.ICartsRepository;
import com.betagames.repository.IDetailsCartsRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.CartsRequest;
import com.betagames.service.interfaces.ICartsService;

@Service
public class CartsImplementation implements ICartsService{

    @Autowired
    IDetailsCartsRepository detailsCartR;

    @Autowired
    ICartsRepository cartR;

    @Autowired
    IUsersRepository usersR;

    @Autowired
	private Logger log;

    @Override
    public  void create(CartsRequest req) throws Exception{

        Date now = new Date();

        Optional<Users> users = usersR.findById(req.getUserId());

        //controllare se l'utente ha già un carrello
        //nel caso sia già stato creato esco da quà e aggiorno solo detailsCart
        if(users.isEmpty()){
        
            Carts carts = new Carts();

            carts.setUser(users.get());//repository
            carts.setCreatedAt(now);
            carts.setUpdatedAt(now);
            carts.setListDetailsCart();//repository

            cartR.save(carts);
        }

    }

    //branch

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
