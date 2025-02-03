package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Carts;



@Repository
public interface ICartsRepository extends JpaRepository<Carts,Integer>{

    //Optional<Carts> findByUser(Integer user);

    //List<Carts> findById(Integer id);

}
