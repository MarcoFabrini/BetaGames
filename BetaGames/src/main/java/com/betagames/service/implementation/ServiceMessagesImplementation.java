package com.betagames.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betagames.model.MessageID;
import com.betagames.model.ServiceMessages;
import com.betagames.repository.IServiceMessagesRepository;
import com.betagames.service.interfaces.IServiceMessagesService;

@Service
public class ServiceMessagesImplementation implements IServiceMessagesService {

    @Autowired
    IServiceMessagesRepository msgRep;

    @Value("${lang}")
    private String lang;

    @Override
    public String getMessage(String code) {
        System.out.println("LINGUA: " + lang);
        Optional<ServiceMessages> msg = msgRep.findById(new MessageID(lang, code));
        String res = null;
        if (msg.isEmpty())
            res = code;
        else
            res = msg.get().getMessage();
        return res;
    }

}
