package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Reviews;

/**
 *
 * @author Fabrini Marco
 */
@Repository
public interface IReviewsRepository extends JpaRepository<Reviews, Integer>{

}// interface
