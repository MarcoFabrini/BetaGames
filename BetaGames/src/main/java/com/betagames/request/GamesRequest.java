package com.betagames.request;

/**
 * @author DorigoLorenzo
 **/

public class GamesRequest {

    private Integer id;
    private String name;
    private String date;
    private Integer minGameTime;
    private Integer maxGameTime;
    private Integer minPlayerNumber;
    private Integer maxPlayerNumber;
    private Integer minAge;
    private String description;
    private Integer stockQuantity;
    private Double price;
    
    private Integer editorsId;
    private Integer authorsId;
    private Integer categoryId;
    private Integer reviewsId;

    // getter and setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getMinGameTime() {
        return minGameTime;
    }

    public void setMinGameTime(Integer minGameTime) {
        this.minGameTime = minGameTime;
    }

    public Integer getMaxGameTime() {
        return maxGameTime;
    }

    public void setMaxGameTime(Integer maxGameTime) {
        this.maxGameTime = maxGameTime;
    }

    public Integer getMinPlayerNumber() {
        return minPlayerNumber;
    }

    public void setMinPlayerNumber(Integer minPlayerNumber) {
        this.minPlayerNumber = minPlayerNumber;
    }

    public Integer getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public void setMaxPlayerNumber(Integer maxPlayerNumber) {
        this.maxPlayerNumber = maxPlayerNumber;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getEditorsId() {
        return editorsId;
    }

    public void setEditorsId(Integer editorsId) {
        this.editorsId = editorsId;
    }

    public Integer getAuthorsId() {
        return authorsId;
    }

    public void setAuthorsId(Integer authorsId) {
        this.authorsId = authorsId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getReviewsId() {
        return reviewsId;
    }

    public void setReviewsId(Integer reviewsId) {
        this.reviewsId = reviewsId;
    }

    //to String

    @Override
    public String toString() {
        return "GamesRequest [id=" + id + ", name=" + name + ", date=" + date + ", minGameTime=" + minGameTime
                + ", maxGameTime=" + maxGameTime + ", minPlayerNumber=" + minPlayerNumber + ", maxPlayerNumber="
                + maxPlayerNumber + ", minAge=" + minAge + ", description=" + description + ", stockQuantity="
                + stockQuantity + ", price=" + price + ", editorsId=" + editorsId + ", authorsId=" + authorsId
                + ", categoryId=" + categoryId + ", reviewsId=" + reviewsId + "]";
    }

    


}// class
