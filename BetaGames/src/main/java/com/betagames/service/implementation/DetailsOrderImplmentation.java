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

}
