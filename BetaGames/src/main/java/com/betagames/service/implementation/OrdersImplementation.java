package com.betagames.service.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.OrdersDTO;
import com.betagames.model.Orders;
import com.betagames.model.PayCards;
import com.betagames.model.Users;
import com.betagames.repository.IOrdersRepository;
import com.betagames.repository.IPayCardsRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.OrdersRequest;
import com.betagames.service.interfaces.IOrdersService;

import static com.betagames.utility.Utilities.buildDetailsOrderDTO;
import static com.betagames.utility.Utilities.buildOrdersDTO;
import static com.betagames.utility.Utilities.buildPayCardsDTO;
import static com.betagames.utility.Utilities.buildUsersDTO;
import static com.betagames.utility.Utilities.convertStringToDate;
/*
 * @author Simone Checco
 */
@Service
public class OrdersImplementation implements IOrdersService{
    @Autowired
    IOrdersRepository orderRep;

    @Autowired
    IUsersRepository userRep;

    @Autowired
    IPayCardsRepository cardRep;

    //metodo che restituisce tutti gli ordini -> testato su postman
    @Override
    public List<OrdersDTO> findAllOrders() throws Exception {
        List<Orders> listOrders = orderRep.findAll();

        return listOrders.stream()
                    .map(order -> new OrdersDTO(order.getId(), order.getTotalAmmount(), order.getOrderStatus(), 
                                        order.getCreatedAt(), order.getUpdatedAt(), null, 
                                        buildDetailsOrderDTO(order.getListDetailsOrder()), null))
                    .collect(Collectors.toList());

    }
    //metodo che restituisce tutti gli ordini fatti da un utente -> testato su postman
    @Override
    public List<OrdersDTO> findByUser(Integer id) throws Exception {
        Optional<Users> user = userRep.findById(id);

        if(user.isEmpty()){
            throw new Exception("User non trovato");
        }

        return buildOrdersDTO(user.get().getListOrders());
    }

    //metodo che restituisce gli ordini tramite la ricerca -> testato su postman
    @Override
    public List<OrdersDTO> searchByTyping() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByTyping'");
    }

    //metodo per creare un ordine
    @Override
    public void create(OrdersRequest req) throws Exception {
        Optional<Users> user = userRep.findById(req.getUserId());
        Optional<Orders> order = orderRep.findById(req.getId());
        Optional<PayCards> card = cardRep.findById(req.getPayCardId());
        /*
         * TODO bisogna anche fare il controllo sulla carta, perchè per adesso accettiamo pagamenti da carte già registrate
         */
        if(user.isEmpty()){
            throw new Exception("User non trovato");
        }

        if(order.isPresent()){
            throw new Exception("Ordine già esistente");
        }

        if(card.isEmpty()){
            throw new Exception("Carta di Pagamento non esistente");
        }
        Orders ord = new Orders();
        ord.setTotalAmmount(req.getTotalAmount());
        ord.setOrderStatus(req.getOrderStatus());
        ord.setCreatedAt(convertStringToDate(req.getCreatedAt()));
        ord.setUpdatedAt(convertStringToDate(req.getUpdatedAt()));
        ord.setUser(user.get());
        ord.setPayCard(card.get());
        
        orderRep.save(ord);
    }

    @Override
    public void update(OrdersRequest req) throws Exception {
        Optional<Orders> order = orderRep.findById(req.getId());

        if(order.isEmpty()){
            throw new Exception("Ordine");
        }

        Orders ord = order.get();
        ord.setTotalAmmount(req.getTotalAmount());
        ord.setOrderStatus(req.getOrderStatus());
        ord.setCreatedAt(convertStringToDate(req.getCreatedAt()));
        ord.setUpdatedAt(convertStringToDate(req.getUpdatedAt()));

        orderRep.save(ord);
    }

    @Override
    public void delete(OrdersRequest req) throws Exception {
        Optional<Orders> order = orderRep.findById(req.getId());

        if(order.isEmpty()){
            throw new Exception("Ordine");
        }

        Orders ord = order.get();
        orderRep.delete(ord);
    }
    
}
