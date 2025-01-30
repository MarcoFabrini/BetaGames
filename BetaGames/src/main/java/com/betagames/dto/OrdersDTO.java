package com.betagames.dto;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Simone Checco
 */

public class OrdersDTO {
    
    private Integer id;

    private Double totalAmount;

    private String orderStatus;

    private Date createdAt;

    private Date updatedAt;

    private UsersDTO userDTO;

    private List<DetailsOrderDTO> listDetailsOrderDTO;

    private PayCardsDTO payCardsDTO;

    public OrdersDTO(Integer id, Double totalAmount, String orderStatus, Date createdAt, Date updatedAt, UsersDTO userDTO,
            List<DetailsOrderDTO> listDetailsOrderDTO, PayCardsDTO payCardsDTO) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userDTO = userDTO;
        this.listDetailsOrderDTO = listDetailsOrderDTO;
        this.payCardsDTO = payCardsDTO;
    }

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

    public UsersDTO getUser() {
        return userDTO;
    }

    public void setUser(UsersDTO userDTO) {
        this.userDTO = userDTO;
    }

    public List<DetailsOrderDTO> getListDetailsOrderDTO() {
        return listDetailsOrderDTO;
    }

    public void setListDetailsOrderDTO(List<DetailsOrderDTO> listDetailsOrderDTO) {
        this.listDetailsOrderDTO = listDetailsOrderDTO;
    }

    public PayCardsDTO getPayCardsDTO() {
        return payCardsDTO;
    }

    public void setPayCardsDTO(PayCardsDTO payCardsDTO) {
        this.payCardsDTO = payCardsDTO;
    }

    @Override
    public String toString() {
        return "OrdersDTO [id=" + id + ", totalAmount=" + totalAmount + ", orderStatus=" + orderStatus + ", createdAt="
                + createdAt + ", updatedAt=" + updatedAt + ", user=" + userDTO + ", listDetailsOrderDTO="
                + listDetailsOrderDTO + ", payCardsDTO=" + payCardsDTO + "]";
    }

    

}
