package com.betagames.dto;

import java.util.Date;

/**
 * @author DorigoLorenzo
 **/

public class PayCardsDTO {
    private Integer id;
    private Integer cardNumber;
    private String cardHolderName;
    private Date expirationDate;
    private Integer cvv;
    private String billingAddress;
    private Date createdAt;
    private Date updatedAt;

    private UsersDTO usersDTO;
    private OrdersDTO ordersDTO;

    //constructors
    public PayCardsDTO(Integer id, Integer cardNumber, String cardHolderName, Date exirationDate, Integer cvv,
            String billingAddress, Date createdAt, Date updatedAt, UsersDTO usersDTO, OrdersDTO ordersDTO) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = exirationDate;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.usersDTO = usersDTO;
        this.ordersDTO = ordersDTO;
    }

    //toString
    @Override
    public String toString() {
        return "PayCardsDTO [id=" + id + ", cardNumber=" + cardNumber + ", cardHolderName=" + cardHolderName
                + ", expirationDate=" + expirationDate + ", cvv=" + cvv + ", billingAddress=" + billingAddress
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", usersDTO=" + usersDTO + ", ordersDTO="
                + ordersDTO + "]";
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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
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

    public UsersDTO getUsersDTO() {
        return usersDTO;
    }

    public void setUsersDTO(UsersDTO usersDTO) {
        this.usersDTO = usersDTO;
    }

    public OrdersDTO getOrdersDTO() {
        return ordersDTO;
    }

    public void setOrdersDTO(OrdersDTO ordersDTO) {
        this.ordersDTO = ordersDTO;
    }    

}//class
