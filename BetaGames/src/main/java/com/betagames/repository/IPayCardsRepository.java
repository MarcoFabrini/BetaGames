package com.betagames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.PayCards;


/**
 * @author DorigoLorenzo
 **/

 @Repository
public interface IPayCardsRepository extends JpaRepository<PayCards, Integer> {

    Optional<PayCards> findByCardNumber(String cardNumber);

    //vedere come fare il find by number ignorando l'id
    //per inserirere più carte con lo stesso numero

    Optional<PayCards> findByCardNumberAndUserId(String cardNumber, Integer userId);
    
    //probabilmente mi servirà un findByIdAndUserId per l'update delle card

}//interface
