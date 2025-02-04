package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Categories;

/**
 *
 * @author Cristhian Guerrero
 */
@Repository
public interface ICategoriesRepository extends JpaRepository<Categories, Integer> {


}// interface
