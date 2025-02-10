package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.DetailsOrder;
import com.betagames.model.Orders;

import java.util.List;


/*
 * 
 * @author Simone Checco
 */
@Repository
public interface IDetailsOrderRepository extends JpaRepository<DetailsOrder,Integer>{
    
    List<DetailsOrder> findByOrder(Orders order);

}
