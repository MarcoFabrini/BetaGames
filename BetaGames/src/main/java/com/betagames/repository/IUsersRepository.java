package com.betagames.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.betagames.model.Users;


/**
 *
 * @author Fabrini Marco
 */
@Repository
public interface IUsersRepository extends JpaRepository<Users, Integer>{

    @Query(name  = "users.searchByTyping")
    List<Users> searchByTyping(@Param("id") Integer id,
                                @Param("username") String username,
                                @Param("email") String email);

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    
}// interface
