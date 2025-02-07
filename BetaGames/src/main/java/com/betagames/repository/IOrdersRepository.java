package com.betagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Orders;

/*
 * 
 * @author Simone Checco
 */
@Repository
public interface IOrdersRepository extends JpaRepository<Orders,Integer>{
    List<Orders> findByUserId(Integer userId);
}
