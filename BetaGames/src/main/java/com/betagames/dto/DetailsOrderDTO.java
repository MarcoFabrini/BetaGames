package com.betagames.dto;

public class DetailsOrderDTO {

    private Integer id;
    private Integer quantity;
    private Double priceAtTime;

    private GamesDTO gameDTO;

    public DetailsOrderDTO(Integer id, Integer quantity, Double priceAtTime, GamesDTO gameDTO) {
        this.id = id;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
        this.gameDTO = gameDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(Double priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    public GamesDTO getGameDTO() {
        return gameDTO;
    }

    public void setGameDTO(GamesDTO gameDTO) {
        this.gameDTO = gameDTO;
    }

    @Override
    public String toString() {
        return "DetailsOrderDTO [id=" + id + ", quantity=" + quantity + ", priceAtTime=" + priceAtTime + ", gameDTO="
                + gameDTO + "]";
    }

}
