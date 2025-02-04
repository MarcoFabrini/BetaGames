package com.betagames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betagames.model.PayCards;

/**
 * @author DorigoLorenzo
 **/

public interface IPayCardsRepository extends JpaRepository<PayCards, Integer> {

    Optional<PayCards> findByCardNumber(Integer cardNumber);

}//interface
