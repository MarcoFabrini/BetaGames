package com.betagames.service.implementation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.DetailsCartDTO;
import com.betagames.model.Carts;
import com.betagames.model.DetailsCart;
import com.betagames.model.Games;
import com.betagames.repository.ICartsRepository;
import com.betagames.repository.IDetailsCartsRepository;
import com.betagames.repository.IGamesRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.DetailsCartRequest;
import com.betagames.service.interfaces.IDetailsCartsService;

import static com.betagames.utility.Utilities.buildDetailsCartsDTO;

@Service
public class DetailsCartsImplementation implements IDetailsCartsService{

    @Autowired
    IDetailsCartsRepository detailsCartR;

    @Autowired
    ICartsRepository cartR;

    @Autowired
    IGamesRepository gamesR;

    @Autowired
    IUsersRepository usersR;

    @Autowired
	  private Logger log;

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void create(DetailsCartRequest req) throws Exception {

        Optional<Carts> carts = cartR.findById(req.getCartId());

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

        //update del carrello
        Date now = new Date();
        carts.get().setUpdatedAt(now);

        cartR.save(carts.get()); 
    }

    //non capisco se serve
    @Transactional(rollbackFor=Exception.class)
    @Override
    public void update(DetailsCartRequest req) throws Exception {

        Optional<DetailsCart> detailsCarts = detailsCartR.findById(req.getId());
        if (detailsCarts.isEmpty())
			throw new Exception("details not found");

        Optional<Games> games = gamesR.findById(detailsCarts.get().getGame().getId());
        if (games.isEmpty())
			throw new Exception("item not found");
        
        Optional<Carts> carts = cartR.findById(detailsCarts.get().getCart().getId());
        if (carts.isEmpty())
			throw new Exception("cart not found");

        DetailsCart dC = detailsCarts.get();

        //dC.setCart(carts.get());
        //admin può fare update del id_carrello...???
        dC.setQuantity(req.getQuantity());
        dC.setPriceAtTime(games.get().getPrice()*req.getQuantity());
        //update del carrello
        Date now = new Date();
        carts.get().setUpdatedAt(now);

        carts.get().getListDetailsCart().add(dC);
        cartR.save(carts.get());
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void delete(DetailsCartRequest req) throws Exception {

        Optional<DetailsCart> detailsCarts = detailsCartR.findById(req.getId());
        if (detailsCarts.isEmpty())
			throw new Exception("details not found");

        Optional<Carts> carts = cartR.findById(detailsCarts.get().getCart().getId());
        if (carts.isEmpty())
			throw new Exception("cart not found");

        detailsCartR.delete(detailsCarts.get());

        //update del carrello
        Date now = new Date();
        carts.get().setUpdatedAt(now);

        cartR.save(carts.get()); 
    }

    //per il checkout
    //@Transactional(rollbackFor=Exception.class)
    @Override
    public void deleteAllByCart(Integer id) throws Exception {

        Optional<Carts> carts = cartR.findById(id);
    
        if (carts.isEmpty())
			throw new Exception("cart not found");

        List<DetailsCart> dCl = detailsCartR.findByCart(carts.get());
        
        detailsCartR.deleteAll(dCl);
        
        //trucco
        //carts.get().setListDetailsCart(null);

        // dCl.forEach(dC ->{
        //     detailsCartR.delete(dC);
        // });

        //====NON RIESCO A CANCELLARE IL CART====

        //carts.get().getListDetailsCart().removeAll(dCl);
        //cartR.save(carts.get());
        //cartR.delete(carts.get());
    }

    @Override
    public List<DetailsCartDTO> list() throws Exception {
        List<DetailsCart> lDetailsCarts = detailsCartR.findAll();
        return buildDetailsCartsDTO(lDetailsCarts);
    }

    @Override
    public List<DetailsCartDTO> listByCarts(Integer id) throws Exception {
        Optional<Carts> carts = cartR.findById(id);
        if(carts.isEmpty()){
            throw new Exception("cart not found");
        }

        List<DetailsCart> lDetailsCarts = detailsCartR.findByCart(carts.get());

        return buildDetailsCartsDTO(lDetailsCarts);
    }


    // private boolean existGame (List<DetailsCart> detailsCart, String search) {
	// 	return detailsCart.stream()
	// 			.map(DetailsCart :: getCart)
	// 			.equals(descrizione -> descrizione.equalsIgnoreCase(search) );
	// }
}
