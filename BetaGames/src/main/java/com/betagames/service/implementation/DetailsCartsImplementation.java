package com.betagames.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.betagames.model.Carts;
import com.betagames.model.DetailsCart;
import com.betagames.model.Games;
import com.betagames.repository.ICartsRepository;
import com.betagames.repository.IDetailsCartsRepository;
import com.betagames.request.DetailsCartRequest;
import com.betagames.service.interfaces.IDetailsCartsService;

public class DetailsCartsImplementation implements IDetailsCartsService{

    @Autowired
    IDetailsCartsRepository detailsCartR;

    @Autowired
    ICartsRepository cartR;

    @Autowired
    IGamesRepository gamesR;

    @Override
    public void create(DetailsCartRequest req) throws Exception {

        Optional<Carts> carts = cartR.findById(req.getCartId());
        Optional<Games> games = gamesR.findById(req.getGameId());

    
        DetailsCart detailsCart = new DetailsCart();

        detailsCart.setCart(carts.get());
        detailsCart.setGame(games.get());
        detailsCart.setQuantity(req.getQuantity());
        detailsCart.setPriceAtTime(games.getPrice()*detailsCart.getQuantity());//repository

        detailsCartR.save(detailsCart);
    }

    @Override
    public void update(DetailsCartRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(DetailsCartRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
