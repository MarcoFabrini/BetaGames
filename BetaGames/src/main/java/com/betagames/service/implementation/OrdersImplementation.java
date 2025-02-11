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
import static com.betagames.utility.Utilities.buildDetailsOrderDTO;
import static com.betagames.utility.Utilities.buildOrdersDTO;
import static com.betagames.utility.Utilities.buildPayCardsDTO;
import static com.betagames.utility.Utilities.convertStringToDate;

/*
 * @author Simone Checco
 */
@Service
public class OrdersImplementation implements IOrdersService {
    @Autowired
    IOrdersRepository orderRep;

    @Autowired
    IUsersRepository userRep;

    @Autowired
    IPayCardsRepository cardRep;

    @Autowired
    IDetailsOrderRepository detailsOrdersRep;

    // ====CART===
    @Autowired
    ICartsRepository cartRep;

    @Autowired
    IDetailsCartsRepository detailsCartRep;

    // metodo che restituisce tutti gli ordini -> testato su postman
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

    // metodo che restituisce tutti gli ordini fatti da un utente -> testato su
    // postman
    @Override
    public List<OrdersDTO> findByUser(Integer id) throws Exception {
        Optional<Users> user = userRep.findById(id);

        if (user.isEmpty()) {
            throw new Exception("User non trovato");
        }

        return buildOrdersDTO(user.get().getListOrders());
    }

    // metodo che restituisce gli ordini tramite la ricerca -> testato su postman
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
        Optional<Users> user = userRep.findById(req.getUserId());
        Optional<PayCards> card = cardRep.findById(req.getPayCardId());

        Optional<Carts> carts = cartRep.findByUser(user.get());
        if (carts.isEmpty()) {
            throw new Exception("cart not found");
        }
        List<DetailsCart> lDetailsCart = detailsCartRep.findByCart(carts.get());
        if (lDetailsCart.isEmpty()) {
            throw new Exception("cart has no items");
        }
        Double totalAmount = lDetailsCart.stream()
                .map(DetailsCart::getPriceAtTime)
                .reduce(0.0, (a, b) -> a + b);
        /*
         * TODO bisogna anche fare il controllo sulla carta, perchè per adesso
         * accettiamo pagamenti da carte già registrate
         */
        if (user.isEmpty()) {
            throw new Exception("User non trovato");
        }

        if (card.isEmpty()) {
            throw new Exception("Carta di Pagamento non esistente");
        }
        Orders ord = new Orders();
        ord.setTotalAmmount(totalAmount);
        ord.setOrderStatus("pending");
        ord.setCreatedAt(now);
        ord.setUpdatedAt(now);
        ord.setUser(user.get());
        ord.setPayCard(card.get());

        Orders newOrd = orderRep.save(ord);

        lDetailsCart.forEach(x -> {
            DetailsOrder detailOrder = new DetailsOrder();
            detailOrder.setPriceAtTime(x.getPriceAtTime());
            detailOrder.setQuantity(x.getQuantity());
            detailOrder.setOrder(newOrd);
            detailOrder.setGame(x.getGame());

            detailsOrdersRep.save(detailOrder);
        });

        detailsCartRep.deleteAll(lDetailsCart);
    }

    @Override
    public void update(OrdersRequest req) throws Exception {
        Optional<Orders> order = orderRep.findById(req.getId());

        if (order.isEmpty()) {
            throw new Exception("Ordine non esistente");
        }

        Orders ord = order.get();
        ord.setTotalAmmount(req.getTotalAmount());
        ord.setOrderStatus(req.getOrderStatus());
        ord.setCreatedAt(convertStringToDate(req.getCreatedAt()));
        ord.setUpdatedAt(convertStringToDate(req.getUpdatedAt()));

        orderRep.save(ord);
    }

}
