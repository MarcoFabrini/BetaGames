package com.betagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Orders;
import com.betagames.model.Users;

/*
 * 
 * @author Simone Checco
 */
@Repository
public interface IOrdersRepository extends JpaRepository<Orders,Integer>{
    List<Orders> findByUserId(Integer userId);

    List<Orders> findByUser(Users user);
}
