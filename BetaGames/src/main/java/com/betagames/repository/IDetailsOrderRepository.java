package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.betagames.model.DetailsOrder;
import com.betagames.model.Games;
import com.betagames.model.Orders;

import java.util.List;
import java.util.Optional;


/*
 * 
 * @author Simone Checco
 */
@Repository
public interface IDetailsOrderRepository extends JpaRepository<DetailsOrder,Integer>{
    
    List<DetailsOrder> findByOrder(Orders order);
    
    @Query(name = "detailsOrder.findOrderForReview")
    Long findOrderForReview(
            @Param("userId") Integer userId,
            @Param("gameId") Integer gameId);

}
