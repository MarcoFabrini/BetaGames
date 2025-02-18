package com.betagames.service.implementation;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.OrdersDTO;
import com.betagames.model.Carts;
import com.betagames.model.DetailsCart;
import com.betagames.model.DetailsOrder;
import com.betagames.model.Orders;
import com.betagames.model.PayCards;
import com.betagames.model.Users;
import com.betagames.repository.ICartsRepository;
import com.betagames.repository.IDetailsCartsRepository;
import com.betagames.repository.IDetailsOrderRepository;
import com.betagames.repository.IOrdersRepository;
import com.betagames.repository.IPayCardsRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.OrdersRequest;
import com.betagames.service.interfaces.IOrdersService;
import com.betagames.service.interfaces.IServiceMessagesService;

import static com.betagames.utility.Utilities.buildDetailsOrderDTO;
import static com.betagames.utility.Utilities.buildOrdersDTO;
import static com.betagames.utility.Utilities.buildPayCardsDTO;

/*
 * @author Simone Checco
 */
@Service
public class OrdersImplementation implements IOrdersService {

    @Autowired
    IServiceMessagesService serviceMessagesService;

    // ====ORDER====
    @Autowired
    IOrdersRepository orderRep;

    @Autowired
    IDetailsOrderRepository detailsOrdersRep;

    // =====USER====
    @Autowired
    IUsersRepository userRep;

    // ====PAY CARD===
    @Autowired
    IPayCardsRepository cardRep;

    // ====CART===
    @Autowired
    ICartsRepository cartRep;

    // ==== DETAILS CART===
    @Autowired
    IDetailsCartsRepository detailsCartRep;

    // metodo che restituisce tutti gli ordini
    @Override
    public List<OrdersDTO> findAllOrders() throws Exception {
        List<Orders> listOrders = orderRep.findAll();

        return listOrders.stream()
                .map(order -> new OrdersDTO(
                        order.getId(),
                        order.getTotalAmmount(),
                        order.getOrderStatus(),
                        order.getCreatedAt(),
                        order.getUpdatedAt(),
                        buildDetailsOrderDTO(order.getListDetailsOrder()),
                        buildPayCardsDTO(order.getPayCard())))
                .collect(Collectors.toList());

    }

    // metodo che restituisce tutti gli ordini fatti da un utente
    @Override
    public List<OrdersDTO> findByUser(Integer id) throws Exception {
        Optional<Users> user = userRep.findById(id);

        if (user.isEmpty()) {
            throw new Exception(serviceMessagesService.getMessage("user-noPresent"));
        }

        return buildOrdersDTO(user.get().getListOrders());
    }

    // metodo che restituisce gli ordini tramite la ricerca
    @Override
    public List<OrdersDTO> searchByTyping(Integer id, Integer idPayCard, Integer idUsers) throws Exception {
        List<Orders> listOrders = orderRep.searchByTyping(id, idPayCard, idUsers);

        return buildOrdersDTO(listOrders);
    }

    // metodo per creare un ordine
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(OrdersRequest req) throws Exception {
        Date now = new Date();
        // controllo l'esistenza dell'utente
        Optional<Users> user = userRep.findById(req.getUserId());

        if (user.isEmpty()) {
            throw new Exception(serviceMessagesService.getMessage("user-noPresent"));
        }

        // controllo l'esistenza del carrello
        Optional<Carts> carts = cartRep.findByUser(user.get());

        if (carts.isEmpty()) {
            throw new Exception(serviceMessagesService.getMessage("cart-noPresent"));
        }
        // controllo l'esitenza di articoli all'interno del carrello
        List<DetailsCart> lDetailsCart = detailsCartRep.findByCart(carts.get());

        if (lDetailsCart.isEmpty()) {
            throw new Exception(serviceMessagesService.getMessage("cart-noItems"));
        }
        // controllo l'esistenza della carta
        Optional<PayCards> card = cardRep.findById(req.getPayCardId());

        if (card.isEmpty()) {
            throw new Exception(serviceMessagesService.getMessage("card-noPresent"));
        }
        // controllo sulla scadenza della carta
        if (card.get().getExpirationDate().compareTo(now) == -1) {
            throw new Exception(serviceMessagesService.getMessage("card-expired"));
        }

        Double totalAmount = lDetailsCart.stream()
                .map(x -> x.getGame().getPrice() * x.getQuantity())
                .reduce(0.0, Double::sum);

        /*
         * TODO bisogna anche fare il controllo sulla carta, perchè per adesso
         * accettiamo pagamenti da carte già registrate
         */

        // creo un nuovo ordine subito dopo aver accettato il pagamento
        Orders ord = new Orders();
        ord.setTotalAmmount(totalAmount);
        ord.setOrderStatus("pending");
        ord.setCreatedAt(now);
        ord.setUpdatedAt(now);
        ord.setUser(user.get());
        ord.setPayCard(card.get());

        Orders newOrd = orderRep.save(ord);

        // creo un lista dettagli ordine facendo riferimento alla lista dettagli
        // carrello
        lDetailsCart.forEach(x -> {
            DetailsOrder detailOrder = new DetailsOrder();
            detailOrder.setPriceAtTime(x.getGame().getPrice() * x.getQuantity());
            detailOrder.setQuantity(x.getQuantity());
            detailOrder.setOrder(newOrd);
            detailOrder.setGame(x.getGame());

            detailsOrdersRep.save(detailOrder);
        });

        detailsCartRep.deleteAll(lDetailsCart);
    }

    // metodo per aggiornare lo stato dell'ordine
    @Override
    public void update(OrdersRequest req) throws Exception {
        Date now = new Date();
        Optional<Orders> order = orderRep.findById(req.getId());

        if (order.isEmpty()) {
            throw new Exception(serviceMessagesService.getMessage("order-noPresent"));
        }

        Orders ord = order.get();
        ord.setOrderStatus(req.getOrderStatus());
        ord.setUpdatedAt(now);

        orderRep.save(ord);
    }

}
