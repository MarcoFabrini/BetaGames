package com.betagames.request;

import java.util.Date;

public class CartsRequest {

    private Integer id;

    private Date createdAt;

    private Date updatedAt;

    private Integer DetailsCartId;

    private Integer userId;

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

    public Integer getDetailsCartId() {
        return DetailsCartId;
    }

    public void setDetailsCartId(Integer detailsCartId) {
        DetailsCartId = detailsCartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
}
