package com.betagames.request;

/**
 * @author DorigoLorenzo
 **/

public class PayCardsRequest {

    private Integer id;
    private Integer cardNumber;
    private Integer cvv;
    private Integer userId;
    private String createdAt;
    private String expirationDate;
    private String updatedAt;
    private String billingAddress;
    private String cardHolderName;

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
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
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

    //toString
    @Override
    public String toString() {
        return "PayCardsRequest [id=" + id + ", cardNumber=" + cardNumber + ", cvv=" + cvv + ", userId=" + userId
                + ", createdAt=" + createdAt + ", expirationDate=" + expirationDate + ", updatedAt=" + updatedAt
                + ", billingAddress=" + billingAddress + ", cardHolderName=" + cardHolderName + "]";
    }
    
}//class
