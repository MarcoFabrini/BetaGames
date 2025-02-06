package com.betagames.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.betagames.model.Categories;

/**
 *
 * @author Cristhian Guerrero
 */
@Repository
public interface ICategoriesRepository extends JpaRepository<Categories, Integer> {
    Optional<Categories> findByName(String name);
    
    @Query(name = "category.searchByTyping")
	List<Categories> searchByFilter(
 			@Param("id") Integer id,
 			@Param("name") String name);
}// interface
