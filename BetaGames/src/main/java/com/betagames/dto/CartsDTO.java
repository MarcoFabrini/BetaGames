package com.betagames.dto;

import java.util.Date;
import java.util.List;

import com.betagames.dto.UsersDTO;
import com.betagames.dto.DetailsCartDTO;

/**
 * @author DorigoLorenzo
 **/

public class CartsDTO {

    private Integer id;
    private Date createdAt;
    private Date updatedAt;

    private List<DetailsCartDTO> listDetailsCartDTO;
    private UsersDTO UserDTO;

    //constructors
    public CartsDTO(Integer id, Date createdAt, Date updatedAt, List<DetailsCartDTO> listDetailsCartDTO, UsersDTO userDTO) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.listDetailsCartDTO = listDetailsCartDTO;
        this.userDTO = userDTO;
    }

    //toString
    @Override
    public String toString() {
        return "CartsDTO [id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", listDetailsCartDTO="
                + listDetailsCartDTO + ", UsersDTO=" + UsersDTO + "]";
    }

    //getter and setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<DetailsCartDTO> getListDetailsCartDTO() {
        return listDetailsCartDTO;
    }

    public void setListDetailsCartDTO(List<DetailsCartDTO> listDetailsCartDTO) {
        this.listDetailsCartDTO = listDetailsCartDTO;
    }

    public UsersDTO getUsersDTO() {
        return UsersDTO;
    }

    public void setUsersDTO(UsersDTO usersDTO) {
        UsersDTO = usersDTO;
    }
 
}//class