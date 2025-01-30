package com.betagames.dto;

/**
 * @author DorigoLorenzo
 **/

public class DetailsCartDTO {

    private Integer id;
    private Double priceAtTime;
    private Integer quantity;

    private GamesDTO gamesDTO;
    private CartsDTO cartsDTO;

    //constructors
    public DetailsCartDTO(Integer id, Double priceAtTime, Integer quantity, GamesDTO gamesDTO, CartsDTO cartsDTO) {
        this.id = id;
        this.priceAtTime = priceAtTime;
        this.quantity = quantity;
        this.gamesDTO = gamesDTO;
        this.cartsDTO = cartsDTO;
    }

    //toString
    @Override
    public String toString() {
        return "DetailsCartDTO [id=" + id + ", priceAtTime=" + priceAtTime + ", quantity=" + quantity + ", gamesDTO="
                + gamesDTO + ", cartsDTO=" + cartsDTO + "]";
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

    public GamesDTO getGamesDTO() {
        return gamesDTO;
    }

    public void setGamesDTO(GamesDTO gamesDTO) {
        this.gamesDTO = gamesDTO;
    }

    public CartsDTO getCartsDTO() {
        return cartsDTO;
    }

    public void setCartsDTO(CartsDTO cartsDTO) {
        this.cartsDTO = cartsDTO;
    }

}//class
