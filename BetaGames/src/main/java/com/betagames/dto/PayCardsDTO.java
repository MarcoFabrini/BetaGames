package com.betagames.dto;

import java.util.Date;

public class PayCardsDTO {
    private Integer id;
    private Integer cardNumber;
    private String cardHolderName;
    private Date exirationDate;
    private Integer cvv;
    private String billingAddress;
    private Date createdAt;
    private Date updatedAt;

    public PayCardsDTO(Integer id, Integer cardNumber, String cardHolderName, Date exirationDate, Integer cvv,
            String billingAddress, Date createdAt, Date updatedAt) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.exirationDate = exirationDate;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    
}//class
