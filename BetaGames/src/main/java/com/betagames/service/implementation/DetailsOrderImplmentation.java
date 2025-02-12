package com.betagames.service.implementation;

import static com.betagames.utility.Utilities.buildDetailsOrderDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.DetailsOrderDTO;
import com.betagames.model.DetailsOrder;
import com.betagames.model.Orders;
import com.betagames.repository.IDetailsOrderRepository;
import com.betagames.repository.IOrdersRepository;
import com.betagames.service.interfaces.IDetailsOrderService;

/*
 * 
 * @author Simone Checco
 */

@Service
public class DetailsOrderImplmentation implements IDetailsOrderService {
    @Autowired
    IDetailsOrderRepository detOrderRep;

    @Autowired
    IOrdersRepository orderRep;

    // stampa i dettagli dell'ordine selezionato
    @Override
    public List<DetailsOrderDTO> searchByOrder(Integer id) throws Exception {
        Optional<Orders> order = orderRep.findById(id);
        if (order.isEmpty()) {
            throw new Exception("Ordine non esistente");
        }

        List<DetailsOrder> listaDettagli = order.get().getListDetailsOrder();
        return buildDetailsOrderDTO(listaDettagli);
    }

    // crea i dettagli dell'ordine in base ai dettagli carrello
    // @Override
    // public void create(DetailsOrderRequest req) throws Exception {
    // Optional<Orders> order = orderRep.findById(req.getOrdersId());
    // Optional<Games> game = gamesRep.findById(req.getGameId());

    // Optional<Users> user = userRep.findById(order.get().getId());
    // Optional<Carts> carts = cartRep.findByUser(user.get());
    // List<DetailsCart> lDetailsCarts = detailsCartRep.findByCart(carts.get());

    // lDetailsCarts.forEach(x -> {
    // System.out.println("soto cilcando");
    // DetailsOrder detailOrder = new DetailsOrder();
    // detailOrder.setPriceAtTime(x.getPriceAtTime());
    // detailOrder.setQuantity(x.getQuantity());
    // detailOrder.setOrder(order.get());
    // detailOrder.setGame(x.getGame());

    // detOrderRep.save(detailOrder);
    // });

    // }

}
