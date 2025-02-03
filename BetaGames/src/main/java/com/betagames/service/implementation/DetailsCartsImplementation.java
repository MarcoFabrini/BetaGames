package com.betagames.service.implementation;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.model.Carts;
import com.betagames.model.DetailsCart;
import com.betagames.model.Games;
import com.betagames.model.Users;
import com.betagames.repository.ICartsRepository;
import com.betagames.repository.IDetailsCartsRepository;
import com.betagames.repository.IGamesRepository;
import com.betagames.request.DetailsCartRequest;
import com.betagames.service.interfaces.IDetailsCartsService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class DetailsCartsImplementation implements IDetailsCartsService{

    @Autowired
    IDetailsCartsRepository detailsCartR;

    @Autowired
    ICartsRepository cartR;

    @Autowired
    IGamesRepository gamesR;

    @Autowired
    ICartsRepository usersR;

    @Autowired
	private Logger log;

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void create(DetailsCartRequest req) throws Exception {

        Optional<Carts> carts = cartR.findById(req.getCartId());
        if (carts.isEmpty()){
            //oppure creo un record Carts...???

            //Optional<Users> users = usersR.findById(req.getCartId());

            // Carts firstCarts = new Carts();
            // Date now = new Date();

            // firstCarts.setUser(users.get());//repository
            // firstCarts.setCreatedAt(now);
            // firstCarts.setUpdatedAt(now);

            // firstCarts.getListDetailsCart().add(detailsCart);//salvo i dettagli nel carrello
            // cartR.save(carts.get()); //update cart

			throw new Exception("cart not found");
        }
        Optional<Games> games = gamesR.findById(req.getGameId());
        if (games.isEmpty())
			throw new Exception("item not found");
        //cerco nella details_cart se esiste già un record con lo stesso gioco e lo stesso carrello
        // if(detailsCartR.existsCart(carts.get()) && detailsCartR.existsGames(games.get()))
        //     throw new Exception("item already in the cart");
    
        DetailsCart detailsCart = new DetailsCart();

        detailsCart.setCart(carts.get());

        detailsCart.setGame(games.get());
        detailsCart.setQuantity(req.getQuantity());
        detailsCart.setPriceAtTime(games.get().getPrice()*req.getQuantity());//repository
        
        detailsCartR.save(detailsCart);
    }

    //non capisco se serve
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void update(DetailsCartRequest req) throws Exception {

        Optional<DetailsCart> detailsCarts = detailsCartR.findById(req.getId());
        if (detailsCarts.isEmpty())
			throw new Exception("details not found");

        Optional<Games> games = gamesR.findById(req.getGameId());
        if (games.isEmpty())
			throw new Exception("item not found");
        
        Optional<Carts> carts = cartR.findById(req.getCartId());
        if (carts.isEmpty())
			throw new Exception("cart not found");

        DetailsCart dC = detailsCarts.get();

        dC.setCart(carts.get());
        //admin può fare update del id_carrello...???
        dC.setQuantity(req.getQuantity());
        dC.setPriceAtTime(games.get().getPrice()*req.getQuantity());
        //update del carrello
        Date now = new Date();
        carts.get().setUpdatedAt(now);

        carts.get().getListDetailsCart().add(dC);
        cartR.save(carts.get());
    }

    @Override
    public void delete(DetailsCartRequest req) throws Exception {

        Optional<DetailsCart> detailsCarts = detailsCartR.findById(req.getId());
        if (detailsCarts.isEmpty())
			throw new Exception("details not found");

        detailsCartR.delete(detailsCarts.get());
    }

    //per il checkout
    //@Transactional(rollbackFor=Exception.class)
    @Override
    public void deleteAllByCart(DetailsCartRequest req) throws Exception {

        Optional<Carts> carts = cartR.findById(req.getCartId());
    
        if (carts.isEmpty())
			throw new Exception("cart not found");

        List<DetailsCart> dCl = detailsCartR.findByCart(carts.get());
        
        //trucco
        //carts.get().setListDetailsCart(null);

        dCl.forEach(dC ->{
            detailsCartR.delete(dC);
        });

        //====NON RIESCO A CANCELLARE IL CART====

        //carts.get().getListDetailsCart().removeAll(dCl);
        //cartR.save(carts.get());
        cartR.delete(carts.get());
    }


    // private boolean existGame (List<DetailsCart> detailsCart, String search) {
	// 	return detailsCart.stream()
	// 			.map(DetailsCart :: getCart)
	// 			.equals(descrizione -> descrizione.equalsIgnoreCase(search) );
	// }

}
