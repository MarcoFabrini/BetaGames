package com.betagames.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.betagames.dto.OrdersDTO;
import com.betagames.request.OrdersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IOrdersService;
@RestController
@RequestMapping("/rest/orders")
public class OrdersController {
    
    @Autowired
    private IOrdersService ordersService;

    @GetMapping("/listOrders")
    public ResponseList<OrdersDTO> listOrders(Integer id){
        ResponseList<OrdersDTO> listResponse = new ResponseList<OrdersDTO>();
        listResponse.setRc(true);

        try {
            listResponse.setData(ordersService.searchByTyping(id));
        } catch (Exception e) {
            listResponse.setRc(false);
            listResponse.setMsg(e.getMessage());
        }
        listResponse.setMsg("lista scaricata con successo");

        return listResponse;
    }

    @PostMapping("/createOrders")
    public ResponseBase create(@RequestBody(required = true) OrdersRequest req){
        ResponseBase response = new ResponseBase();
        System.out.println("Richiesta: " + req.toString());
        response.setRc(true);
        response.setMsg("Ordine Creato con successo");
        try {
            ordersService.create(req);
        } catch (Exception e) {
            response.setRc(false);
            response.setMsg(e.getMessage());
        }
        

        return response;
    }
}
