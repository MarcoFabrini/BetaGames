package com.betagames.dto;

import java.util.Date;

/**
 *
 * @author Fabrini Marco
 */
public class ReviewsDTO {
    private Integer id;
    private Integer score;
    private String description;
    private Date createdAt;
    private UsersDTO usersDTO;
    private GamesDTO gamesDTO;

    public ReviewsDTO(Integer id, Integer score, String description, Date createdAt, UsersDTO usersDTO,
            GamesDTO gamesDTO) {
        this.id = id;
        this.score = score;
        this.description = description;
        this.createdAt = createdAt;
        this.usersDTO = usersDTO;
        this.gamesDTO = gamesDTO;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ReviewsDTO{");
        sb.append("id=").append(id);
        sb.append(", score=").append(score);
        sb.append(", description=").append(description);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", usersDTO=").append(usersDTO);
        sb.append(", gamesDTO=").append(gamesDTO);
        sb.append('}');
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UsersDTO getUsersDTO() {
        return usersDTO;
    }

    public void setUsersDTO(UsersDTO usersDTO) {
        this.usersDTO = usersDTO;
    }

    public GamesDTO getGamesDTO() {
        return gamesDTO;
    }

    public void setGamesDTO(GamesDTO gamesDTO) {
        this.gamesDTO = gamesDTO;
    }

}// class
