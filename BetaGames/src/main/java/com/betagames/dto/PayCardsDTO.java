package com.betagames.dto;

import java.util.Date;

import com.betagames.dto.UsersDTO;
import com.betagames.dto.OrdersDTO;


/**
 * @author DorigoLorenzo
 **/

public class PayCardsDTO {
    private Integer id;
    private Integer cardNumber;
    private String cardHolderName;
    private Date exirationDate;
    private Integer cvv;
    private String billingAddress;
    private Date createdAt;
    private Date updatedAt;

    private UsersDTO userDTO;
    private OrdersDTO orderDTO;

    //constructors
    public PayCardsDTO(Integer id, Integer cardNumber, String cardHolderName, Date exirationDate, Integer cvv,
            String billingAddress, Date createdAt, Date updatedAt, UsersDTO userDTO, OrdersDTO orderDTO) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.exirationDate = exirationDate;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userDTO = userDTO;
        this.orderDTO = orderDTO;
    }

    //toString
    @Override
    public String toString() {
        return "PayCardsDTO [id=" + id + ", cardNumber=" + cardNumber + ", cardHolderName=" + cardHolderName
                + ", exirationDate=" + exirationDate + ", cvv=" + cvv + ", billingAddress=" + billingAddress
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", userDTO=" + userDTO + ", orderDTO="
                + orderDTO + "]";
    }

    //getter and setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public Date getExirationDate() {
        return exirationDate;
    }

    public void setExirationDate(Date exirationDate) {
        this.exirationDate = exirationDate;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
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

    public UsersDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UsersDTO userDTO) {
        this.userDTO = userDTO;
    }

    public OrdersDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrdersDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

}//class
