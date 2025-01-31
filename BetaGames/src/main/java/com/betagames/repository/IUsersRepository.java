package com.betagames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Users;

/**
 *
 * @author Fabrini Marco
 */
@Repository
public interface IUsersRepository extends JpaRepository<Users, Integer>{

}// interface
