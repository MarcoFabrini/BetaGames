package com.betagames.dto;

/**
 * @author DorigoLorenzo
 **/

public class DetailsCartDTO {

    private Integer id;
    private Integer quantity;

    private GamesDTO gamesDTO;

    public DetailsCartDTO(Integer id, Integer quantity, GamesDTO gamesDTO) {
        this.id = id;
        this.quantity = quantity;
        this.gamesDTO = gamesDTO;
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

    public GamesDTO getGamesDTO() {
        return gamesDTO;
    }

    public void setGamesDTO(GamesDTO gamesDTO) {
        this.gamesDTO = gamesDTO;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DetailsCartDTO{");
        sb.append("id=").append(id);
        sb.append(", quantity=").append(quantity);
        sb.append(", gamesDTO=").append(gamesDTO);
        sb.append('}');
        return sb.toString();
    }

}// class
