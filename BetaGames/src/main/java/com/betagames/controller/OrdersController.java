package com.betagames.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.betagames.dto.OrdersDTO;
import com.betagames.request.OrdersRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;
import com.betagames.service.interfaces.IOrdersService;

/*
 * 
 * @author Simone Checco
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;

    @GetMapping("admin/orders/allOrders")
    public ResponseList<OrdersDTO> listOrders() {
        ResponseList<OrdersDTO> responseList = new ResponseList<OrdersDTO>();
        responseList.setRc(true);

        try {
            responseList.setData(ordersService.findAllOrders());
        } catch (Exception e) {
            responseList.setRc(false);
            responseList.setMsg(e.getMessage());
        }

        return responseList;
    }

    @GetMapping("admin/orders/searchByTyping")
    public ResponseList<OrdersDTO> searchByTyping(@RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "payCardId", required = false) Integer payCardId,
            @RequestParam(value = "userId", required = false) Integer userId) {
        ResponseList<OrdersDTO> list = new ResponseList<OrdersDTO>();
        list.setRc(true);
        try {
            System.out.println("PayCard: " + payCardId);
            list.setData(ordersService.searchByTyping(id, payCardId, userId));
        } catch (Exception e) {
            list.setRc(false);
            list.setMsg(e.getMessage());
        }
        return list;
    }

    @GetMapping("user/orders/userOrders")
    public ResponseList<OrdersDTO> listOrdersByUsers(@RequestParam(value = "id") Integer id) {
        ResponseList<OrdersDTO> listResponse = new ResponseList<OrdersDTO>();
        listResponse.setRc(true);

        try {
            listResponse.setData(ordersService.findByUser(id));
        } catch (Exception e) {
            listResponse.setRc(false);
            listResponse.setMsg(e.getMessage());
        }
        listResponse.setMsg("lista scaricata con successo");

        return listResponse;
    }

    @PostMapping("user/orders/createOrders")
    public ResponseBase create(@RequestBody(required = true) OrdersRequest req) {
        ResponseBase response = new ResponseBase();
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

    @PostMapping("admin/orders/updateOrders")
    public ResponseBase update(@RequestBody(required = true) OrdersRequest req) {
        ResponseBase response = new ResponseBase();
        response.setRc(true);
        response.setMsg("Ordine Aggiornato con successo");
        try {
            ordersService.update(req);
        } catch (Exception e) {
            response.setRc(false);
            response.setMsg(e.getMessage());
        }

        return response;
    }

}
