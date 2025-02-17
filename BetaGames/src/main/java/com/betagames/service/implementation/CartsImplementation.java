package com.betagames.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.CartsDTO;
import com.betagames.model.Carts;
import com.betagames.repository.ICartsRepository;
import com.betagames.service.interfaces.ICartsService;
import static com.betagames.utility.Utilities.buildCartsDTO;

@Service
public class CartsImplementation implements ICartsService{



    @Autowired
    ICartsRepository cartR;



    // @Override
    // public  void create(CartsRequest req) throws Exception{

    //     Date now = new Date();

    //     Optional<Users> users = usersR.findById(req.getUserId());

    //     Optional<Carts> cartsUser = cartR.findByUser(users.get());

    //     if (users.isEmpty())
	// 		throw new Exception("user not found");

    //     //gestisco l'errore delle chiavi duplicate
    //     if(cartsUser.isPresent())
    //         throw new Exception("user can have one cart");
        
    //     Carts carts = new Carts();

    //     carts.setUser(users.get());
    //     carts.setCreatedAt(now);
    //     carts.setUpdatedAt(now);

    //     cartR.save(carts);
    // }

    // @Override
    // public void delete(CartsRequest req) throws Exception {

    //     Optional<Carts> carts = cartR.findById(req.getId());
    //     if (carts.isEmpty())
	// 		throw new Exception("cart not found");
    //     cartR.delete(carts.get());
    // }

    @Override
	public List<CartsDTO> list() throws Exception {
		List<Carts> lC = cartR.findAll();
        
		return buildCartsDTO(lC);
	}
    
}
