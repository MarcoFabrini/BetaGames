package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.OrdersDTO;
import com.betagames.request.OrdersRequest;

/*
 * 
 * @author Simone Checco
 */

public interface IOrdersService {

    List<OrdersDTO> findAllOrders() throws Exception;

    List<OrdersDTO> findByUser(Integer id) throws Exception;

    List<OrdersDTO> searchByTyping(Integer id, Integer idPayCard, Integer idUsers) throws Exception;

    void create(OrdersRequest req) throws Exception;

    void update(OrdersRequest req) throws Exception;
}
