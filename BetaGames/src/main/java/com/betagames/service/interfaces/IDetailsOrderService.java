package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.DetailsOrderDTO;
import com.betagames.request.DetailsOrderRequest;

public interface IDetailsOrderService {
    
    List<DetailsOrderDTO> searchByTyping() throws Exception;

    void create(DetailsOrderRequest req) throws Exception;

    void update(DetailsOrderRequest req) throws Exception;

    void delete(DetailsOrderRequest req) throws Exception;
}
