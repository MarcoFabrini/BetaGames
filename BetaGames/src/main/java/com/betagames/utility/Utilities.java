package com.betagames.utility;

import java.util.List;
import java.util.stream.Collectors;

import com.betagames.dto.EditorsDTO;
import com.betagames.model.Editors;

public class Utilities {

    public static EditorsDTO buildEditorsDTO(Editors e) {
        return new EditorsDTO(
                e.getId(),
                e.getName(),
                e.getWebsite());
    }// buildEditorsDTO

    public static List<EditorsDTO> buildEditorsDTO(List<Editors> e) {
        return e.stream()
                .map(ed -> new EditorsDTO(
                        ed.getId(),
                        ed.getName(),
                        ed.getWebsite()))
                .collect(Collectors.toList());
    }// buildEditorsDTO

    public static ReviewsDTO buildReviewsDTO(Reviews r) {
        return new ReviewsDTO(
                r.getId(),
                r.getScore(),
                r.getDescription(),
                r.getCreatedAt(),
                buildUsersDTO(r.getUser()),
                buildGamesDTO(r.getGame()));
    }// buildReviewsDTO

    public static List<ReviewsDTO> buildReviewsDTO(List<Reviews> r) {
        return r.stream()
                .map(re -> new ReviewsDTO(
                        re.getId(),
                        re.getScore(),
                        re.getDescription(),
                        re.getCreatedAt(),
                        buildUsersDTO(re.getUser()),
                        buildGamesDTO(re.getGame())))
                .collect(Collectors.toList());
    }// buildReviewsDTO

    public static UsersDTO buildUsersDTO(Users u) {
        return new UsersDTO(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getPwd(),
                buildReviewsDTO(u.getListReviews()),
                buildOrdersDTO(u.getListOrders()),
                buildPayCardsDTO(u.getListPayCards())
        );
    }// buildUsersDTO

    public static List<UsersDTO> buildUsersDTO(List<Users> u){
        return u.stream()
                .map(us -> new UsersDTO(
                    us.getId(),
                    us.getUsername(),
                    us.getEmail(),
                    us.getPwd(),
                    buildReviewsDTO(us.getListReviews()),
                    buildOrdersDTO(us.getListOrders()),
                    buildPayCardsDTO(us.getListPayCards()) 
                ))
                .collect(Collectors.toList());
    }// buildUsersDTO

}// class
