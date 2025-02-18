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
import com.betagames.service.interfaces.IServiceMessagesService;

import static com.betagames.utility.Utilities.buildDetailsCartsDTO;

@Service
public class DetailsCartsImplementation implements IDetailsCartsService {

    @Autowired
    IServiceMessagesService serviceMessagesService;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(DetailsCartRequest req) throws Exception {

        Optional<Carts> carts = cartR.findById(req.getCartId());

        Optional<Games> games = gamesR.findById(req.getGameId());
        if (games.isEmpty())
            throw new Exception(serviceMessagesService.getMessage("game-id"));
        // cerco nella details_cart se esiste già un record con lo stesso gioco e lo
        // stesso carrello
        if (detailsCartR.existsByCartAndGame(carts.get(), games.get())) {
            throw new Exception(serviceMessagesService.getMessage("cartItem-present"));
        }

        DetailsCart detailsCart = new DetailsCart();

        detailsCart.setCart(carts.get());
        detailsCart.setGame(games.get());
        detailsCart.setQuantity(req.getQuantity());

        detailsCartR.save(detailsCart);

        // update del carrello
        Date now = new Date();
        carts.get().setUpdatedAt(now);

        cartR.save(carts.get());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(DetailsCartRequest req) throws Exception {

        Optional<DetailsCart> detailsCarts = detailsCartR.findById(req.getId());
        if (detailsCarts.isEmpty())
            throw new Exception(serviceMessagesService.getMessage("cartItem-noPresent"));

        Optional<Games> games = gamesR.findById(detailsCarts.get().getGame().getId());
        if (games.isEmpty())
            throw new Exception(serviceMessagesService.getMessage("game-noPresent"));

        Optional<Carts> carts = cartR.findById(detailsCarts.get().getCart().getId());
        if (carts.isEmpty())
            throw new Exception(serviceMessagesService.getMessage("cart-noPresent"));

        DetailsCart dC = detailsCarts.get();

        if (req.getQuantity() <= 0) {
            delete(req);
        }

        dC.setQuantity(req.getQuantity());
        // update del carrello
        Date now = new Date();
        carts.get().setUpdatedAt(now);

        carts.get().getListDetailsCart().add(dC);
        cartR.save(carts.get());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(DetailsCartRequest req) throws Exception {

        Optional<DetailsCart> detailsCarts = detailsCartR.findById(req.getId());
        if (detailsCarts.isEmpty())
            throw new Exception(serviceMessagesService.getMessage("cartItem-noPresent"));

        Optional<Carts> carts = cartR.findById(detailsCarts.get().getCart().getId());
        if (carts.isEmpty())
            throw new Exception(serviceMessagesService.getMessage("cart-noPresent"));

        detailsCartR.delete(detailsCarts.get());

        // update orario del carrello del carrello
        Date now = new Date();
        carts.get().setUpdatedAt(now);

        cartR.save(carts.get());
    }

    @Override
    public void deleteAllByCart(DetailsCartRequest req) throws Exception {

        Optional<Carts> carts = cartR.findById(req.getCartId());

        if (carts.isEmpty())
            throw new Exception(serviceMessagesService.getMessage("cart-noPresent"));

        List<DetailsCart> dCl = detailsCartR.findByCart(carts.get());

        detailsCartR.deleteAll(dCl);
    }

    @Override
    public List<DetailsCartDTO> list() throws Exception {
        List<DetailsCart> lDetailsCarts = detailsCartR.findAll();
        return buildDetailsCartsDTO(lDetailsCarts);
    }

    @Override
    public List<DetailsCartDTO> listByCarts(Integer id) throws Exception {
        Optional<Carts> carts = cartR.findById(id);
        if (carts.isEmpty()) {
            throw new Exception(serviceMessagesService.getMessage("cart-noPresent"));
        }

        List<DetailsCart> lDetailsCarts = detailsCartR.findByCart(carts.get());

        return buildDetailsCartsDTO(lDetailsCarts);
    }
}
