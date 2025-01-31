package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Orders;

/*
 * 
 * @author Simone Checco
 */
@Repository
public interface IOrdersRepository extends JpaRepository<Orders,Integer>{
    
}
