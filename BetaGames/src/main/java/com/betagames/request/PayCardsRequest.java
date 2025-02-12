package com.betagames.request;

import java.util.Date;

/**
 * @author DorigoLorenzo
 **/

public class PayCardsRequest {

    private Integer id;
    private String cardNumber;
    private Integer cvv;
    private Integer userId;
    private Date createdAt;
    private String expirationDate;
    private Date updatedAt;
    private String billingAddress;
    private String cardHolderName;

    // getter and setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {

        this.updatedAt = updatedAt;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    // toString
    @Override
    public String toString() {
        return "PayCardsRequest [id=" + id + ", cardNumber=" + cardNumber + ", cvv=" + cvv + ", userId=" + userId
                + ", createdAt=" + createdAt + ", expirationDate=" + expirationDate + ", updatedAt=" + updatedAt
                + ", billingAddress=" + billingAddress + ", cardHolderName=" + cardHolderName + "]";
    }

}// class
