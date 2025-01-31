package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.PayCardsDTO;
import com.betagames.request.PayCardsRequest;

/**
 * @author DorigoLorenzo
 **/

public interface IPayCardsService {

    List<PayCardsDTO> searchByTyping() throws Exception;

    void create(PayCardsRequest req) throws Exception;

    void update(PayCardsRequest req) throws Exception;

    void delete(PayCardsRequest req) throws Exception;

}//interface
