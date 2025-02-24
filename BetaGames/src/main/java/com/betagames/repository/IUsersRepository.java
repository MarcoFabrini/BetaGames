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
 * @author FabriniMarco
 */
@Repository
public interface IUsersRepository extends JpaRepository<Users, Integer> {

    @Query(name = "users.searchByTyping")
    List<Users> searchByTyping(@Param("id") Integer id,
            @Param("username") String username,
            @Param("email") String email,
            @Param("active") Boolean active);

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    Optional<Users> findTopBy();

    Optional<Users> findByUsernameAndPwd(String username, String pwd);

    // esclude se stesso dal db e cerca negli altri record 
    Optional<Users> findByUsernameAndIdNot(String username, Integer id);
    
    Optional<Users> findByEmailAndIdNot(String email, Integer id);
}// interface
