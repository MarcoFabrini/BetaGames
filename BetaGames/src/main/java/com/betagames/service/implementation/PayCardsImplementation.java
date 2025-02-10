package com.betagames.service.implementation;

import static com.betagames.utility.Utilities.buildPayCardsDTO;
import static com.betagames.utility.Utilities.convertStringToDate;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betagames.dto.PayCardsDTO;
import com.betagames.model.PayCards;
import com.betagames.model.Users;
import com.betagames.repository.IPayCardsRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.PayCardsRequest;
import com.betagames.service.interfaces.IPayCardsService;

/**
 * @author DorigoLorenzo
 **/

@Service
public class PayCardsImplementation implements IPayCardsService {

    @Autowired
    IPayCardsRepository paycardsR;

    @Autowired
    IUsersRepository usersR;

    @Autowired
    Logger log;

    // @Override
    // public List<PayCardsDTO> searchByTyping() throws Exception {
    // return null;
    // }

    @Override
    public List<PayCardsDTO> list() throws Exception {
        List<PayCards> listPayCards = paycardsR.findAll();
        return buildPayCardsDTO(listPayCards);
    }// list

    @Override
    public List<PayCardsDTO> listByUser(Integer id) throws Exception {
        Optional<Users> user = usersR.findById(id);
        if (user.isEmpty()) {
            throw new Exception("User non trovato");
        }
        return buildPayCardsDTO(user.get().getListPayCards());
    }//listByUser

    @Override
    public void create(PayCardsRequest req) throws Exception {

        //Verifico che non esistano già altre carte associate allo stesso user con lo stesso cardNumber
        //2 user diversi dovrebbero poter avere una carta con lo stesso numero (esempio padre e figlio)
        Optional<PayCards> paycard = paycardsR.findByCardNumberAndUserId(req.getCardNumber(), req.getUserId());
        if (paycard.isPresent()){
            throw new Exception("Pay Card already present for this user!");

        }
        // recupero lo user di riferimento
        Optional<Users> user = usersR.findById(req.getUserId());
        PayCards p = new PayCards(); // nuova carta
        // popolo i campi
        p.setCardNumber(req.getCardNumber());
        p.setCvv(req.getCvv());
        p.setCardHolderName(req.getCardHolderName());
        p.setUser(user.get()); // speriamo bene
        p.setCreatedAt(req.getCreatedAt());
        p.setUpdatedAt(req.getUpdatedAt());
        p.setExpirationDate(convertStringToDate(req.getExpirationDate()));
        p.setBillingAddress(req.getBillingAddress());
        p.setActive(true);
        // save
        paycardsR.save(p);
    }// create

    @Override
    public void update(PayCardsRequest req) throws Exception {


        //controlli da rivedere
        Optional<PayCards> paycard = paycardsR.findById(req.getId());
        if(!paycard.isPresent()) throw new Exception("Pay Card not found!");
        paycard = paycardsR.findByCardNumber(req.getCardNumber());
        if(!paycard.isPresent()) throw new Exception("Pay Card not found!");

        //controllo da rivedere per far si che non si possa andare a duplicare una carta con lo stesso user
            // paycard = paycardsR.findByCardNumberAndUserId(req.getCardNumber(), req.getUserId());
            // if(!paycard.isPresent()) throw new Exception("Pay Card not found!");

        Optional<Users> user = usersR.findById(req.getUserId());    //recupero lo user di riferimento
        PayCards updtPayCard = paycard.get();       //creo la paycard che contiene i campi updatati (PayCardUpdated)
        //popolo i campi
        updtPayCard.setCardNumber(req.getCardNumber());
        updtPayCard.setCvv(req.getCvv());
        updtPayCard.setCardHolderName(req.getCardHolderName());
        updtPayCard.setUser(user.get());              //CAPIRE SE SERVE ANDARE A MODIFICARE L'USER... HA SENSO?
        updtPayCard.setCreatedAt((req.getCreatedAt()) );     //mettere che non possa essere cambiato(?) 
        updtPayCard.setUpdatedAt((req.getUpdatedAt()));      //impostarlo in automatico all'update

        updtPayCard.setExpirationDate(convertStringToDate(req.getExpirationDate()));
        updtPayCard.setBillingAddress(req.getBillingAddress());
        // save
        paycardsR.save(updtPayCard);
    }// update

    @Override
    public void delete(PayCardsRequest req) throws Exception {
        Optional<PayCards> paycard = paycardsR.findById(req.getId());
        if (!paycard.isPresent())
            throw new Exception("Pay Card not found!");

        paycard.get().setActive(false);

        paycardsR.save(paycard.get());
    }// delete

}// class
