package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.DetailsOrder;

/*
 * 
 * @author Simone Checco
 */
@Repository
public interface IDetailsOrderRepository extends JpaRepository<DetailsOrder,Integer>{
    
}
