package com.betagames.service.implementation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.CartsDTO;
import com.betagames.model.Carts;
import com.betagames.model.DetailsCart;
import com.betagames.model.Users;
import com.betagames.repository.ICartsRepository;
import com.betagames.repository.IDetailsCartsRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.CartsRequest;
import com.betagames.service.interfaces.ICartsService;

import static com.betagames.utility.Utilities.buildCartsDTO;

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
    @Transactional(rollbackFor=Exception.class)
    public  void create(CartsRequest req) throws Exception{

        Date now = new Date();

        Optional<Users> users = usersR.findById(req.getUserId());

        Optional<Carts> cartsUser = cartR.findByUser(users.get());

        //Optional<DetailsCart> detailsCart = detailsCartR.findById(req.getUserId());

        //controllare se l'utente ha già un carrello
        //nel caso sia già stato creato esco da quà e aggiorno solo detailsCart

        if (users.isEmpty())
			throw new Exception("user not found");

        //gestisco l'errore delle chiavi duplicate
        if(cartsUser.isPresent())
            throw new Exception("user can have one cart");
        
        Carts carts = new Carts();

        carts.setUser(users.get());//repository
        carts.setCreatedAt(now);
        carts.setUpdatedAt(now);
        //i dettagliCarello li creo in contemporanea
        //carts.setListDetailsCart();//repository

        cartR.save(carts);
    }

    @Override
    public void delete(CartsRequest req) throws Exception {

        Optional<Carts> carts = cartR.findById(req.getId());
        if (carts.isEmpty())
			throw new Exception("cart not found");
        cartR.delete(carts.get());
    }

    @Override
	public List<CartsDTO> list() throws Exception {
		List<Carts> lC = cartR.findAll();
        
		return buildCartsDTO(lC);
	}
    
}
