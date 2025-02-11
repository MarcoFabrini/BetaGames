package com.betagames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Roles;
/*
 * 
 * @author Simone Checco
 */

 @Repository
public interface IRolesRepository extends JpaRepository<Roles,Integer>{

    Optional<Roles> findByName(String name);

    Optional<Roles> findByNameIgnoreCase(String name);
}
