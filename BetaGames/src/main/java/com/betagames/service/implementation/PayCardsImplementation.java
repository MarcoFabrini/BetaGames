package com.betagames.service.implementation;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.betagames.dto.PayCardsDTO;
import com.betagames.repository.IPayCardsRepository;
import com.betagames.request.PayCardsRequest;
import com.betagames.service.interfaces.IPayCardsService;

/**
 * @author DorigoLorenzo
 **/

public class PayCardsImplementation implements IPayCardsService{

    @Autowired
    IPayCardsRepository paycardsR;

    @Autowired
    Logger log;

    @Override
    public List<PayCardsDTO> searchByTyping() throws Exception {
        return null;
    }

    @Override
    public void create(PayCardsRequest req) throws Exception {
        //
    }

    @Override
    public void update(PayCardsRequest req) throws Exception {
        //
    }

    @Override
    public void delete(PayCardsRequest req) throws Exception {
        //
    }

}//class
