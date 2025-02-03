package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Authors;

/**
 *
 * @author Cristhian Guerrero
 */
@Repository
public interface IAuthorsRepository extends JpaRepository<Authors, Integer>{


  @Query(name = "authors.searchByTyping")
	List<Authors> searchByFilter(
			@Param("id") Integer id,
			@Param("nome") String nome,
			@Param("lastname") String lastname,
			@Param("country") String country,
            @Param("biography") String biography,
            @Param("gameId") Integer gameId
			);
}// interface
