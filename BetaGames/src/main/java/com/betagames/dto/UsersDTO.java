package com.betagames.dto;

import java.util.List;

/**
 *
 * @author FabriniMarco
 */
public class UsersDTO {
    private Integer id;
    private String username;
    private String email;
    private List<OrdersDTO> listOrdersDTO;
    private CartsDTO carts;
    private List<PayCardsDTO> listPayCardsDTO;
    private RolesDTO role;

    public UsersDTO(Integer id, String username, String email, List<OrdersDTO> listOrdersDTO, CartsDTO carts,
            List<PayCardsDTO> listPayCardsDTO, RolesDTO role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.listOrdersDTO = listOrdersDTO;
        this.carts = carts;
        this.listPayCardsDTO = listPayCardsDTO;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrdersDTO> getListOrdersDTO() {
        return listOrdersDTO;
    }

    public void setListOrdersDTO(List<OrdersDTO> listOrdersDTO) {
        this.listOrdersDTO = listOrdersDTO;
    }

    public CartsDTO getCarts() {
        return carts;
    }

    public void setCarts(CartsDTO carts) {
        this.carts = carts;
    }

    public List<PayCardsDTO> getListPayCardsDTO() {
        return listPayCardsDTO;
    }

    public void setListPayCardsDTO(List<PayCardsDTO> listPayCardsDTO) {
        this.listPayCardsDTO = listPayCardsDTO;
    }

    public RolesDTO getRole() {
        return role;
    }

    public void setRole(RolesDTO role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UsersDTO [id=" + id + ", username=" + username + ", email=" + email
                + ", listOrdersDTO=" + listOrdersDTO + ", carts=" + carts + ", listPayCardsDTO=" + listPayCardsDTO
                + ", role=" + role + "]";
    }

}// class
