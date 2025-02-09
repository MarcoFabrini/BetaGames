package com.betagames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Carts;
import com.betagames.model.DetailsCart;
import com.betagames.model.Games;

@Repository
public interface IDetailsCartsRepository extends JpaRepository<DetailsCart, Integer> {

    List<DetailsCart> findByCart(Carts cart);

    // se il gioco è già stato scelto
    List<DetailsCart> findByGame(Games game);

    boolean existsByCartAndGame(Carts cart, Games game);

}
