package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.OrdersDTO;
import com.betagames.request.OrdersRequest;

public interface IOrdersService {
    
    List<OrdersDTO> searchByTyping(Integer id) throws Exception;

    void create(OrdersRequest req) throws Exception;

    void update(OrdersRequest req) throws Exception;

    void delete(OrdersRequest req ) throws Exception;
}
