package com.betagames.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Carts;
import com.betagames.model.Users;

@Repository
public interface ICartsRepository extends JpaRepository<Carts, Integer> {

    Optional<Carts> findByUser(Users user);

}
