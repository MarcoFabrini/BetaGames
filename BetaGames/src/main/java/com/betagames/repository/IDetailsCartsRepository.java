package com.betagames.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.betagames.model.Carts;
import com.betagames.model.DetailsCart;
import com.betagames.model.Games;

import java.util.List;


@Repository
public interface IDetailsCartsRepository extends JpaRepository<DetailsCart,Integer>{

    List<DetailsCart> findByCart(Carts cart);

    //se il gioco è già stato scelto
    List<DetailsCart> findByGame(Games game);


}
