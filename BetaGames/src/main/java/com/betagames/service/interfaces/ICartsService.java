package com.betagames.service.interfaces;
import java.util.List;

import com.betagames.dto.CartsDTO;
import com.betagames.request.CartsRequest;

public interface ICartsService {

    void create(CartsRequest req) throws Exception;
    void update(CartsRequest req) throws Exception;
    void delete(CartsRequest req) throws Exception;

    //visulizzo il carrello del utente con annesso il details_cart
    CartsDTO listCartsByUser(Integer id) throws Exception;

    List<CartsDTO> list() throws Exception;

}
