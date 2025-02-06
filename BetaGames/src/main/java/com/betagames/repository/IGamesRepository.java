package com.betagames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Games;

/**
 * @author DorigoLorenzo
 **/

 @Repository
public interface IGamesRepository extends JpaRepository<Games, Integer> {

    Optional<Games> findByName(String name);
    


}//interface
