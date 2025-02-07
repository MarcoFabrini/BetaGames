package com.betagames.service.implementation;

import static com.betagames.utility.Utilities.buildDetailsOrderDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.DetailsOrderDTO;
import com.betagames.model.DetailsOrder;
import com.betagames.model.Games;
import com.betagames.model.Orders;
import com.betagames.repository.IDetailsOrderRepository;
import com.betagames.repository.IGamesRepository;
import com.betagames.repository.IOrdersRepository;
import com.betagames.request.DetailsOrderRequest;
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

    @Autowired
    IGamesRepository gamesRep;

    @Override
    public List<DetailsOrderDTO> searchByOrder(Integer id) throws Exception {
        Optional<Orders> order = orderRep.findById(id);
        if (order.isEmpty()) {
            throw new Exception("Ordine non esistente");
        }

        List<DetailsOrder> listaDettagli = order.get().getListDetailsOrder();
        listaDettagli.forEach(d -> System.out.println(d.getQuantity()));
        return buildDetailsOrderDTO(listaDettagli);
    }

    @Override
    public void create(DetailsOrderRequest req) throws Exception {
        Optional<Orders> order = orderRep.findById(req.getOrdersId());
        Optional<Games> game = gamesRep.findById(req.getGameId());

        if (game.get().getStockQuantity() < req.getQuantity()) {
            throw new Exception("quantità non disponibile");
        }

        DetailsOrder detailOrder = new DetailsOrder();
        detailOrder.setPriceAtTime(req.getPriceAtTime());
        detailOrder.setQuantity(req.getQuantity());
        detailOrder.setOrder(order.get());
        detailOrder.setGame(game.get());

        detOrderRep.save(detailOrder);
    }

    @Override
    public void update(DetailsOrderRequest req) throws Exception {
        Optional<Orders> order = orderRep.findById(req.getOrdersId());
        Optional<Games> game = gamesRep.findById(req.getGameId());
        Optional<DetailsOrder> detOrder = detOrderRep.findById(req.getGameId());

        if (order.isEmpty()) {
            throw new Exception("id dettaglio ordine non trovato non trovato");
        }

        if (game.get().getStockQuantity() < req.getQuantity()) {
            throw new Exception("quantità non disponibile");
        }

        DetailsOrder detailOrder = detOrder.get();
        detailOrder.setId(req.getId());
        detailOrder.setPriceAtTime(req.getPriceAtTime());
        detailOrder.setQuantity(req.getQuantity());
        detailOrder.setOrder(order.get());
        detailOrder.setGame(game.get());

        detOrderRep.save(detailOrder);
    }

    @Override
    public void delete(DetailsOrderRequest req) throws Exception {
        Optional<DetailsOrder> detOrder = detOrderRep.findById(req.getId());

        if (detOrder.isEmpty()) {
            throw new Exception("Dettagli ordine non esistente");
        }

        DetailsOrder detailOrder = detOrder.get();
        detOrderRep.delete(detailOrder);
    }

}
