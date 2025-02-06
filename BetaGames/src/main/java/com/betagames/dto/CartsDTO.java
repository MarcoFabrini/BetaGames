package com.betagames.dto;

import java.util.Date;
import java.util.List;

/**
 * @author DorigoLorenzo
 **/

public class CartsDTO {

    private Integer id;
    private Date createdAt;
    private Date updatedAt;

    private List<DetailsCartDTO> listDetailsCartDTO;

    public CartsDTO(Integer id, Date createdAt, Date updatedAt, List<DetailsCartDTO> listDetailsCartDTO) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.listDetailsCartDTO = listDetailsCartDTO;
    }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CartsDTO{");
        sb.append("id=").append(id);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", listDetailsCartDTO=").append(listDetailsCartDTO);
        sb.append('}');
        return sb.toString();
    }

}// class