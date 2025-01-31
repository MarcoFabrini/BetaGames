package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betagames.model.PayCards;

/**
 * @author DorigoLorenzo
 **/

public interface IPayCardsRepository extends JpaRepository<PayCards, Integer> {

}//interface
