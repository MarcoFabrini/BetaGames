package com.betagames.dto;

import java.util.Date;

/**
 * @author DorigoLorenzo
 **/

public class PayCardsDTO {
    private Integer id;
    private String cardNumber;
    private String cardHolderName;
    private Date expirationDate;
    private Integer cvv;
    private String billingAddress;
    private Date createdAt;
    private Date updatedAt;
    private Boolean active;


    public PayCardsDTO(Integer id, String cardNumber, String cardHolderName, Date expirationDate, Integer cvv,
            String billingAddress, Date createdAt, Date updatedAt, Boolean active) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.billingAddress = billingAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
    }

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
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "PayCardsDTO [id=" + id + ", cardNumber=" + cardNumber + ", cardHolderName=" + cardHolderName
                + ", expirationDate=" + expirationDate + ", cvv=" + cvv + ", billingAddress=" + billingAddress
                + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", active=" + active + "]";
    }


}// class
