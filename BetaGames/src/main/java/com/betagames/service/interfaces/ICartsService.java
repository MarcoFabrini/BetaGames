package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.CartsDTO;
import com.betagames.request.CartsRequest;

public interface ICartsService {

    // void create(CartsRequest req) throws Exception;

    // void delete(CartsRequest req) throws Exception;

    List<CartsDTO> list() throws Exception;

}
