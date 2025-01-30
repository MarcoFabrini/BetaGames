package com.betagames.dto;

import com.betagames.dto.CartsDTO;
import com.betagames.dto.GamesDTO;

/**
 * @author DorigoLorenzo
 **/

public class DetailsCartDTO {

    private Integer id;
    private Double priceAtTime;
    private Integer quantity;

    private GamesDTO gameDTO;
    private CartsDTO cartDTO;

    //constructors
    public DetailsCartDTO(Integer id, Double priceAtTime, Integer quantity, GamesDTO gameDTO, CartsDTO cartDTO) {
        this.id = id;
        this.priceAtTime = priceAtTime;
        this.quantity = quantity;
        this.gameDTO = gameDTO;
        this.cartDTO = cartDTO;
    }

    //toString
    @Override
    public String toString() {
        return "DetailsCartDTO [id=" + id + ", priceAtTime=" + priceAtTime + ", quantity=" + quantity + ", gameDTO="
                + gameDTO + ", cartDTO=" + cartDTO + "]";
    }

    //getter and setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(Double priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public GamesDTO getGameDTO() {
        return gameDTO;
    }

    public void setGameDTO(GamesDTO gameDTO) {
        this.gameDTO = gameDTO;
    }

    public CartsDTO getCartDTO() {
        return cartDTO;
    }

    public void setCartDTO(CartsDTO cartDTO) {
        this.cartDTO = cartDTO;
    }
        
}//class
