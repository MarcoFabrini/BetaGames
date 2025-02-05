package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Reviews;

/**
 *
 * @author FabriniMarco
 */
@Repository
public interface IReviewsRepository extends JpaRepository<Reviews, Integer>{

}// interface
