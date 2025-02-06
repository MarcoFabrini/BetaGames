package com.betagames.dto;

import java.util.List;

/**
 *
 * @author Cristhian Guerrero
 */
public class CategoriesDTO {

    private Integer id;
    private String name;
    private List<GamesDTO> listGamesDTO;

    public CategoriesDTO() {
    }

    public CategoriesDTO(Integer id, String name, List<GamesDTO> listGamesDTO) {
        this.id = id;
        this.name = name;
        this.listGamesDTO = listGamesDTO;
    }

    @Override
    public String toString() {
        return "CategoriesDTO [id=" + id + ", name=" + name + ", listGamesDTO=" + listGamesDTO + "]";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GamesDTO> getListGamesDTO() {
        return listGamesDTO;
    }

    public void setListGamesDTO(List<GamesDTO> listGamesDTO) {
        this.listGamesDTO = listGamesDTO;
    }

}
