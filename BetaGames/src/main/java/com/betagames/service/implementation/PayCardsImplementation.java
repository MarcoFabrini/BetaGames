package com.betagames.service.implementation;

import static com.betagames.utility.Utilities.buildPayCardsDTO;
import static com.betagames.utility.Utilities.convertStringToDate;

import java.util.Date;
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
import com.betagames.service.interfaces.IServiceMessagesService;

/**
 * @author DorigoLorenzo
 **/

@Service
public class PayCardsImplementation implements IPayCardsService {

    @Autowired
    IServiceMessagesService serviceMessagesService;
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
            throw new Exception(serviceMessagesService.getMessage("user-noPresent"));
        }
        return buildPayCardsDTO(user.get().getListPayCards());
    }// listByUser

    @Override
    public void create(PayCardsRequest req) throws Exception {
        // Verifico che non esistano già altre carte associate allo stesso user con lo
        // stesso cardNumber
        // 2 user diversi dovrebbero poter avere una carta con lo stesso numero (esempio
        // padre e figlio)
        Optional<PayCards> paycard = paycardsR.findByCardNumberAndUserId(req.getCardNumber(), req.getUserId());
        if (paycard.isPresent())
            throw new Exception(serviceMessagesService.getMessage("card-Present"));
        // recupero lo user di riferimento
        Optional<Users> user = usersR.findById(req.getUserId());
        PayCards p = new PayCards(); // nuova carta
        // popolo i campi
        p.setCardNumber(req.getCardNumber());
        p.setCvv(req.getCvv());
        p.setCardHolderName(req.getCardHolderName());
        p.setUser(user.get()); // speriamo bene
        // Creo la data con l'ora corrente, sia per la creazione che per l'aggiornamento
        Date now = new Date();
        p.setCreatedAt(now);
        p.setUpdatedAt(now);
        // la data di scandenza deve comunque essere inserita dall'utente
        p.setExpirationDate(convertStringToDate(req.getExpirationDate()));
        p.setBillingAddress(req.getBillingAddress());
        p.setActive(true);
        // save
        paycardsR.save(p);
    }// create

    @Override
    public void update(PayCardsRequest req) throws Exception {
        // Recupero la carta tramite id della req
        Optional<PayCards> paycard = paycardsR.findById(req.getId());
        if (!paycard.isPresent())
            throw new Exception(serviceMessagesService.getMessage("card-noPresent"));
        // Se c'è mi salvo la carta vecchia in una carta fittizia (updatablePayCard)
        PayCards updtPayCard = paycard.get();
        // Verifico se il numero di carta è stato modificato
        if (!updtPayCard.getCardNumber().equals(req.getCardNumber())) {
            // Se il numero è stato modificato, verifico che il nuovo numero non sia già in
            // uso da un'altra carta dello stesso utente
            Optional<PayCards> existingCard = paycardsR.findByCardNumberAndUserId(req.getCardNumber(),
                    updtPayCard.getUser().getId()); // ID utente PRESO DALLA CARTA
            if (existingCard.isPresent() && !existingCard.get().getId().equals(req.getId())) {
                throw new Exception(serviceMessagesService.getMessage("card-Present"));
            }
            // 4. Aggiorna il numero di carta
            updtPayCard.setCardNumber(req.getCardNumber());
        }
        // aggiornamento degli altri campi
        updtPayCard.setCvv(req.getCvv());
        updtPayCard.setCardHolderName(req.getCardHolderName());
        // updtPayCard.setUser(user.get()); // CAPIRE SE SERVE ANDARE A MODIFICARE
        // L'USER... HA SENSO? ------> NO
        // aggiornamento data di update
        Date now = new Date(); // data e ora corrente
        // updtPayCard.setCreatedAt(now); // mettere che non possa essere cambiato(?)
        updtPayCard.setUpdatedAt(now); // impostarlo in automatico all'update (al momento della creazione sarà uguale
                                       // alla createdAt)

        updtPayCard.setExpirationDate(convertStringToDate(req.getExpirationDate())); // richiasto l'inserimento
        updtPayCard.setBillingAddress(req.getBillingAddress());
        // save
        paycardsR.save(updtPayCard);
    }// update

    @Override
    public void delete(PayCardsRequest req) throws Exception {
        Optional<PayCards> paycard = paycardsR.findById(req.getId());
        if (!paycard.isPresent())
            throw new Exception(serviceMessagesService.getMessage("card-noPresent"));

        paycard.get().setActive(false);
        paycardsR.save(paycard.get());
    }// delete

}// class
