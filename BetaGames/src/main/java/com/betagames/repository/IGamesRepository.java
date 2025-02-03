package com.betagames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betagames.model.Games;

/**
 * @author DorigoLorenzo
 **/

public interface IGamesRepository extends JpaRepository<Games, Integer> {

    Optional<Games> findByName(String name);
    


}//interface
