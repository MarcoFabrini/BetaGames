package com.betagames.utility;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.betagames.dto.OrdersDTO;
import com.betagames.model.Orders;
import com.betagames.dto.CartsDTO;
import com.betagames.dto.DetailsCartDTO;
import com.betagames.dto.EditorsDTO;
import com.betagames.model.Carts;
import com.betagames.model.DetailsCart;
import com.betagames.model.Editors;

public class Utilities {
  
  public static List<OrdersDTO> buildOrdersDTO(List<Orders> listOrders){
        return listOrders.stream()
                        .map(order -> new OrdersDTO(order.getId(), order.getTotalAmmount(),order.getOrderStatus(), order.getCreatedAt(), order.getUpdatedAt(), 
                                                    null, null, null)) //settati a null perchè mancano i buildDTO
                        .collect(Collectors.toList());
    }
    private final static String PATTERN_DATE = "dd/MM/yyyy";
	  public static Date convertStringToDate(String dataString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DATE, Locale.ITALY);
		return formatter.parse(dataString);
	}

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

    // public static ReviewsDTO buildReviewsDTO(Reviews r) {
    //     return new ReviewsDTO(
    //             r.getId(),
    //             r.getScore(),
    //             r.getDescription(),
    //             r.getCreatedAt(),
    //             buildUsersDTO(r.getUser()),
    //             buildGamesDTO(r.getGame()));
    // }// buildReviewsDTO

    // public static List<ReviewsDTO> buildReviewsDTO(List<Reviews> r) {
    //     return r.stream()
    //             .map(re -> new ReviewsDTO(
    //                     re.getId(),
    //                     re.getScore(),
    //                     re.getDescription(),
    //                     re.getCreatedAt(),
    //                     buildUsersDTO(re.getUser()),
    //                     buildGamesDTO(re.getGame())))
    //             .collect(Collectors.toList());
    // }// buildReviewsDTO

    // public static UsersDTO buildUsersDTO(Users u) {
    //     return new UsersDTO(
    //             u.getId(),
    //             u.getUsername(),
    //             u.getEmail(),
    //             u.getPwd(),
    //             buildReviewsDTO(u.getListReviews()),
    //             buildOrdersDTO(u.getListOrders()),
    //             buildPayCardsDTO(u.getListPayCards())
    //     );
    // }// buildUsersDTO

    // public static List<UsersDTO> buildUsersDTO(List<Users> u){
    //     return u.stream()
    //             .map(us -> new UsersDTO(
    //                 us.getId(),
    //                 us.getUsername(),
    //                 us.getEmail(),
    //                 us.getPwd(),
    //                 buildReviewsDTO(us.getListReviews()),
    //                 buildOrdersDTO(us.getListOrders()),
    //                 buildPayCardsDTO(us.getListPayCards()) 
    //             ))
    //             .collect(Collectors.toList());
    // }// buildUsersDTO

  // public static List<CartsDTO> buildCartsDTO(List<Carts> c){

  //   return c.stream()
  //     .map(cart -> new CartsDTO(
  //       cart.getId(),
  //       cart.getCreatedAt(),
  //       cart.getUpdatedAt(),
  //       buildUsersDTO(cart.getUsersDTO())
  //     ))
  //     .collect(Collectors.toList());
  // }
  // public static CartsDTO buildCartsDTO(Carts c){

  //   return new CartsDTO(
  //       c.getId(),
  //       c.getCreatedAt(),
  //       c.getUpdatedAt(),
  //       buildUsersDTO(c.getUsersDTO())
  //     );
  // }

  // public static List<DetailsCartDTO> buildDetailsCartsDTO(List<DetailsCart> dC){

  //   return dC.stream()
  //     .map(dCart -> new DetailsCartDTO(
  //       dCart.getId(),
  //       dCart.getPriceAtTime(),
  //       dCart.getQuantity(),
  //       buildGamesDTO(dCart.getGamesDTO()),
  //       buildCartsDTO(dCart.getCartsDTO())
  //     ))
  //     .collect(Collectors.toList());
  // }
  // public static DetailsCartDTO buildDetailsCartsDTO(DetailsCart dC){

  //   return new DetailsCartDTO(
  //     dC.getId(),
  //     dC.getPriceAtTime(),
  //     dC.getQuantity(),
  //     buildGamesDTO(dC.getGamesDTO()),
  //     buildCartsDTO(dC.getCartsDTO())
  //   );
  // }

}// class
