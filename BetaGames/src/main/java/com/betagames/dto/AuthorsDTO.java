package com.betagames.dto;

/**
 *
 * @author Simone Checco
 */

public class AuthorsDTO {

    private Integer id;

    private String biography;

    private String country;

    private String lastname;

    private String name;

    

    public AuthorsDTO(Integer id, String biography, String country, String lastname, String name) {
        this.id = id;
        this.biography = biography;
        this.country = country;
        this.lastname = lastname;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorsDTO [id=" + id + ", biography=" + biography + ", country=" + country + ", lastname=" + lastname
                + ", name=" + name + "]";
    }

}
