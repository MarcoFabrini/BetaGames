package com.betagames.service.implementation;

import static com.betagames.utility.Utilities.buildPayCardsDTO;

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
public class PayCardsImplementation implements IPayCardsService{

    @Autowired
    IPayCardsRepository paycardsR;

    @Autowired
    IUsersRepository usersR;

    @Autowired
    Logger log;

    // @Override
    // public List<PayCardsDTO> searchByTyping() throws Exception {
    //     return null;
    // }

    @Override
	public List<PayCardsDTO> list() throws Exception {
        List<PayCards> listPayCards = paycardsR.findAll();
        return buildPayCardsDTO(listPayCards);
	}//list

    @Override
    public void create(PayCardsRequest req) throws Exception {
        //verifico che non esistano carte con lo stesso numero
        Optional<PayCards> paycard = paycardsR.findByCardNumber(req.getCardNumber());
        if (paycard.isPresent()){
            throw new Exception("Pay Card already present!");
        }
        //recupero lo user di riferimento
        Optional<Users> user = usersR.findById(req.getUserId());
        PayCards p = new PayCards();            //nuova carta
        //popolo i campi
        p.setCardNumber(req.getCardNumber());
        p.setCvv(req.getCvv());
        p.setCardHolderName(req.getCardHolderName());
        p.setUser(user.get());                  //speriamo bene
        p.setCreatedAt(req.getCreatedAt());
        p.setUpdatedAt(req.getUpdatedAt());
        p.setExpirationDate(req.getExpirationDate());
        p.setBillingAddress(req.getBillingAddress());
        //save
        paycardsR.save(p);
    }//create

    @Override
    public void update(PayCardsRequest req) throws Exception {
        //recupero la carta per id e controllo che id e numero carta esistano
        //situazione da rivedere (magari con numero carta e id corrispondente... 
        //forse attualmente potrebbero mandarmi a carte diverse in qualche caso)
        Optional<PayCards> paycard = paycardsR.findById(req.getId());
        if(!paycard.isPresent())
            throw new Exception("Pay Card not found");
        paycard = paycardsR.findByCardNumber(req.getCardNumber());
        if(!paycard.isPresent())
            throw new Exception("Pay Card not found");
        
        Optional<Users> user = usersR.findById(req.getUserId());    //recuper lo user di riferimento
        PayCards updtPayCard = paycard.get();       //creo la paycard che contiene i campi updatati (PayCardUpdated)
        //popolo i campi
        updtPayCard.setCardNumber(req.getCardNumber());
        updtPayCard.setCvv(req.getCvv());
        updtPayCard.setCardHolderName(req.getCardHolderName());
        updtPayCard.setUser(user.get());                  //speriamo bene
        updtPayCard.setCreatedAt(req.getCreatedAt());
        updtPayCard.setUpdatedAt(req.getUpdatedAt());
        updtPayCard.setExpirationDate(req.getExpirationDate());
        updtPayCard.setBillingAddress(req.getBillingAddress());
        //save
        paycardsR.save(updtPayCard);
    }//update

    @Override
    public void delete(PayCardsRequest req) throws Exception {
        Optional<PayCards> paycard = paycardsR.findById(req.getId());
        if(!paycard.isPresent())
            throw new Exception("Pay Card not found!");
        
        paycardsR.delete(paycard.get());
    }//delete

}//class
