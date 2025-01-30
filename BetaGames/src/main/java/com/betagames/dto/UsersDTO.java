package com.betagames.dto;

import java.util.List;

/**
 *
 * @author Fabrini Marco
 */
public class UsersDTO {
    private Integer id;
    private String username;
    private String email;
    private String pwd;
    private List<ReviewsDTO> listReviewsDTO;
    private List<OrdersDTO> listOrdersDTO;
    private List<PayCardsDTO> listPayCardsDTO;

    public UsersDTO(Integer id, String username, String email, String pwd, List<ReviewsDTO> listReviewsDTO,
            List<OrdersDTO> listOrdersDTO, List<PayCardsDTO> listPayCardsDTO) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.pwd = pwd;
        this.listReviewsDTO = listReviewsDTO;
        this.listOrdersDTO = listOrdersDTO;
        this.listPayCardsDTO = listPayCardsDTO;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UsersDTO{");
        sb.append("id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", email=").append(email);
        sb.append(", pwd=").append(pwd);
        sb.append(", listReviewsDTO=").append(listReviewsDTO);
        sb.append(", listOrdersDTO=").append(listOrdersDTO);
        sb.append(", listPayCardsDTO=").append(listPayCardsDTO);
        sb.append('}');
        return sb.toString();
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public List<ReviewsDTO> getListReviewsDTO() {
        return listReviewsDTO;
    }

    public void setListReviewsDTO(List<ReviewsDTO> listReviewsDTO) {
        this.listReviewsDTO = listReviewsDTO;
    }

    public List<OrdersDTO> getListOrdersDTO() {
        return listOrdersDTO;
    }

    public void setListOrdersDTO(List<OrdersDTO> listOrdersDTO) {
        this.listOrdersDTO = listOrdersDTO;
    }

    public List<PayCardsDTO> getListPayCardsDTO() {
        return listPayCardsDTO;
    }

    public void setListPayCardsDTO(List<PayCardsDTO> listPayCardsDTO) {
        this.listPayCardsDTO = listPayCardsDTO;
    }

}// class
