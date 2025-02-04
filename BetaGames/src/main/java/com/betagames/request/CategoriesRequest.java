package com.betagames.request;

/**
 *
 * @author Cristhian Guerrero
 */
public class CategoriesRequest {
    private Integer id;
    private String name;
    private Integer gamesId;

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
    public Integer getGamesId() {
        return gamesId;
    }
    public void setGamesId(Integer gamesId) {
        this.gamesId = gamesId;
    }

    
}
