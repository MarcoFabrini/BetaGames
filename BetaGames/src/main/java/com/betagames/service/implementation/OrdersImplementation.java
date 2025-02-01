package com.betagames.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.OrdersDTO;
import com.betagames.model.Orders;
import com.betagames.model.Users;
import com.betagames.repository.IOrdersRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.OrdersRequest;
import com.betagames.service.interfaces.IOrdersService;
import static com.betagames.utility.Utilities.buildOrdersDTO;
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

    @Override
    public List<OrdersDTO> searchByTyping(Integer id) throws Exception {
        
        Optional<Users> user = userRep.findById(id);

        if(user.isEmpty()){
            throw new Exception("User non trovato");
        }

        return buildOrdersDTO(user.get().getListOrders());
    }

    @Override
    public void create(OrdersRequest req) throws Exception {
        Optional<Users> user = userRep.findById(req.getUserId());
        Optional<Orders> order = orderRep.findById(req.getId());

        /*
         * TODO bisogna anche fare il controllo sulla carta, perchè per adesso accettiamo pagamenti da carte già registrate
         */
        if(user.isEmpty()){
            throw new Exception("User non trovato");
        }

        if(order.isPresent()){
            throw new Exception("Ordine già esistente");
        }
        Orders ord = new Orders();
        ord.setTotalAmmount(req.getTotalAmount());
        ord.setCreatedAt(convertStringToDate(req.getCreatedAt()));
        ord.setUpdatedAt(convertStringToDate(req.getUpdatedAt()));
        ord.setUser(user.get());
        ord.setPayCard(null); //settato a null finchè non è presente la repository di payCard
        
        orderRep.save(ord);
    }

    @Override
    public void update(OrdersRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(OrdersRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
