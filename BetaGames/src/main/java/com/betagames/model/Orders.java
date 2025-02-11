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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_ammount")
    private Double totalAmmount;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;


    @ManyToOne
    @JoinColumn(name = "id_users", nullable = true)
    private Users user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<DetailsOrder> listDetailsOrder;

    @ManyToOne
    @JoinColumn(name = "id_pay_cards", referencedColumnName = "id")
    private PayCards payCard;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalAmmount() {
        return totalAmmount;
    }

    public void setTotalAmmount(Double totalAmmount) {
        this.totalAmmount = totalAmmount;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<DetailsOrder> getListDetailsOrder() {
        return listDetailsOrder;
    }

    public void setListDetailsOrder(List<DetailsOrder> listDetailsOrder) {
        this.listDetailsOrder = listDetailsOrder;
    }

    public PayCards getPayCard() {
        return payCard;
    }

    public void setPayCard(PayCards payCard) {
        this.payCard = payCard;
    }

}// class
