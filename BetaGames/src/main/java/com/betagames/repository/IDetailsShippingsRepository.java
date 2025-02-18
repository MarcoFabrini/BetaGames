package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.DetailsShipping;
@Repository
public interface IDetailsShippingsRepository extends JpaRepository<DetailsShipping, Integer>{
	
}// interface
