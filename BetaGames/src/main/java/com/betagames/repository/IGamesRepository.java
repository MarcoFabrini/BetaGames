package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betagames.model.Games;

/**
 * @author DorigoLorenzo
 **/

public interface IGamesRepository extends JpaRepository<Games, Integer> {

}//interface
