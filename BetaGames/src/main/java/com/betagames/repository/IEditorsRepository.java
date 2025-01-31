package com.betagames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Editors;

/**
 *
 * @author Fabrini Marco
 */
@Repository
public interface IEditorsRepository extends JpaRepository<Editors, Integer>{
    Optional<Editors> findByName(String name);

}// interface
