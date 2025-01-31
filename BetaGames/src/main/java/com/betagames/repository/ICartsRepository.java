package com.betagames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Carts;
import java.util.List;



@Repository
public interface ICartsRepository extends JpaRepository<Carts,Integer>{

    //Optional<Carts> findByUser(Integer user);

    List<Carts> findById(Integer id);

}
