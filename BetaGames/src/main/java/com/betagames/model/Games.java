package com.betagames.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pubblication_date")
    private Date date;

    @Column(name = "min_game_time")
    private Integer minGameTime;

    @Column(name = "max_game_time")
    private Integer maxGameTime;

    @Column(name = "min_player_number")
    private Integer minPlayerNumber;

    @Column(name = "max_player_number")
    private Integer maxPlayerNumber;

    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "description")
    private String description;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToMany(mappedBy = "list_games", fetch = FetchType.EAGER)
    private List<Authors> listAuthors;

    @ManyToMany(mappedBy = "list_games", fetch = FetchType.EAGER)
    private List<Categories> listCategory;

    @ManyToOne
    @JoinColumn(name = "id_editors")
    private Editors editor;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private List<DetailsCart> listDetailsCarts;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private List<Reviews> listReviews;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private List<DetailsOrder> listDetailsOrder;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Editors getEditor() {
        return editor;
    }

    public void setEditor(Editors editor) {
        this.editor = editor;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Authors> getListAuthors() {
        return listAuthors;
    }

    public void setListAuthors(List<Authors> listAuthors) {
        this.listAuthors = listAuthors;
    }

    public List<Categories> getListCategory() {
        return listCategory;
    }

    public void setListCategory(List<Categories> listCategory) {
        this.listCategory = listCategory;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<DetailsCart> getListDetailsCarts() {
        return listDetailsCarts;
    }

    public void setListDetailsCarts(List<DetailsCart> listDetailsCarts) {
        this.listDetailsCarts = listDetailsCarts;
    }

    public List<Reviews> getListReviews() {
        return listReviews;
    }

    public void setListReviews(List<Reviews> listReviews) {
        this.listReviews = listReviews;
    }

    public List<DetailsOrder> getListDetailsOrder() {
        return listDetailsOrder;
    }

    public void setListDetailsOrder(List<DetailsOrder> listDetailsOrder) {
        this.listDetailsOrder = listDetailsOrder;
    }

}// class
