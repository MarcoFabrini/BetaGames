package com.betagames.service.interfaces;


import java.util.List;

import com.betagames.dto.DetailsCartDTO;

import com.betagames.request.DetailsCartRequest;

public interface IDetailsCartsService {

    void create(DetailsCartRequest req) throws Exception;
    void update(DetailsCartRequest req) throws Exception;
    void delete(DetailsCartRequest req) throws Exception;


    List<DetailsCartDTO> list()throws Exception;
    List<DetailsCartDTO> listByCarts(Integer id)throws Exception;
    
    //per il checkout
    void deleteAllByCart(Integer id) throws Exception;

}
