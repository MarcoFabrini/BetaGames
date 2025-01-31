package com.betagames.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.DetailsCart;

@Repository
public interface IDetailsCartsRepository extends JpaRepository<DetailsCart,Integer>{

}
