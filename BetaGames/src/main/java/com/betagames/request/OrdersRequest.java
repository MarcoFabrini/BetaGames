package com.betagames.request;


/*
 * 
 * @author Simone Checco
 */

public class OrdersRequest {
    private Integer id;

    private Double totalAmount;

    private String orderStatus;

    private String createdAt;

    private String updatedAt;

    private Integer userId;

    private Integer payCardId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPayCardId() {
        return payCardId;
    }

    public void setPayCardId(Integer payCardId) {
        this.payCardId = payCardId;
    }

    @Override
    public String toString() {
        return "OrdersRequest [id=" + id + ", totalAmount=" + totalAmount + ", orderStatus=" + orderStatus
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", userId=" + userId + ", payCardId="
                + payCardId + "]";
    }

    
    
}
