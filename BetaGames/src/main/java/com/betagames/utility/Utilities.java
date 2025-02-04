package com.betagames.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.betagames.dto.EditorsDTO;
import com.betagames.dto.OrdersDTO;

import com.betagames.dto.ReviewsDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.model.Editors;
import com.betagames.model.Orders;

import com.betagames.dto.CartsDTO;
import com.betagames.dto.DetailsCartDTO;
import com.betagames.dto.EditorsDTO;
import com.betagames.model.Carts;
import com.betagames.model.DetailsCart;

import com.betagames.model.Reviews;
import com.betagames.model.Users;

import com.betagames.dto.PayCardsDTO;
import com.betagames.model.Orders;
import com.betagames.model.PayCards;

import com.betagames.dto.RolesDTO;
import com.betagames.model.Orders;
import com.betagames.dto.AuthorsDTO;
import com.betagames.model.Authors;
import com.betagames.model.Roles;
import com.betagames.dto.DetailsOrderDTO;
import com.betagames.model.DetailsOrder;

import com.betagames.model.Editors;
import com.betagames.model.Games;
import com.betagames.model.Orders;
import com.betagames.model.PayCards;
import com.betagames.model.Reviews;
import com.betagames.model.Roles;
import com.betagames.model.Users;

public class Utilities {

  // builder per farsi restituire la lista di OrdersDTO
  public final static List<OrdersDTO> buildOrdersDTO(List<Orders> listOrders) {
    return listOrders.stream()
        .map(order -> new OrdersDTO(order.getId(), order.getTotalAmmount(), order.getOrderStatus(),
            order.getCreatedAt(), order.getUpdatedAt(),
            null, null, null)) // settati a null perchè mancano i buildDTO
        .collect(Collectors.toList());
  }

  // builder per farsi restituire la lista di RolesDTO
  public final static List<RolesDTO> buildRolesDTO(List<Roles> listRoles) {
    return listRoles.stream()
        .map(role -> new RolesDTO(role.getId(), role.getName(), null)) // settato a null perchè manca buildUsersDTO
        .collect(Collectors.toList());
  }

  // builder per farsi restituire la lista DetailsOrderDTO
  public final static List<DetailsOrderDTO> buildDetailsOrderDTO(List<DetailsOrder> listDetailsOrder) {
    return listDetailsOrder.stream()
        .map(detail -> new DetailsOrderDTO(detail.getId(), detail.getQuantity(), detail.getPriceAtTime(),
            buildOrdersDTO(detail.getOrder()), null))
        .collect(Collectors.toList());
  }

  // builder per farsi restituire un OrdersDTO
  public final static OrdersDTO buildOrdersDTO(Orders o) {
    return new OrdersDTO(o.getId(), o.getTotalAmmount(), o.getOrderStatus(),
        o.getCreatedAt(), o.getUpdatedAt(), null, null, null);
  }

  // build per Il singolo RolesDTO
  public final static RolesDTO buildRolesDTO(Roles r) {
    return new RolesDTO(
        r.getId(),
        r.getName(),
        buildUsersDTO(r.getListUsers()));
  }

  // builder per il singolo DetailsOrderDTO
  public final static DetailsOrderDTO buildDetailsOrderDTO(DetailsOrderDTO d) {
    return new DetailsOrderDTO(d.getId(), d.getQuantity(), d.getPriceAtTime(), d.getOrderDTO(), null);
  }

  public static UsersDTO buildUsersDTO(Users u) {
    return new UsersDTO(
        u.getId(),
        u.getUsername(),
        u.getEmail(),
        u.getPwd(),
        buildReviewsDTO(u.getListReviews()),
        buildOrdersDTO(u.getListOrders()),
        buildPayCardsDTO(u.getListPayCards()),
        buildRolesDTO(u.getRole()));
  }// buildUsersDTO

  public static List<UsersDTO> buildUsersDTO(List<Users> u) {
    return u.stream()
        .map(us -> new UsersDTO(null, PATTERN_DATE, PATTERN_DATE, PATTERN_DATE, null, null, null, null))
        .collect(Collectors.toList());
  }// buildUsersDTO

  private final static String PATTERN_DATE = "dd/MM/yyyy";

  public static Date convertStringToDate(String dataString) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DATE, Locale.ITALY);
    return formatter.parse(dataString);
  }

  public static GamesDTO buildGamesDTO(Games g) {
    return new GamesDTO(
        g.getId(),
        g.getName(),
        g.getDate(),
        g.getMinGameTime(),
        g.getMaxGameTime(),
        g.getMinPlayerNumber(),
        g.getMaxPlayerNumber(),
        g.getMinAge(),
        g.getDescription(),
        g.getStockQuantity(),
        g.getPrice(),
        buildEditorsDTO(g.getEditor()),
        buildCategoryDTO(g.getListCategory()),
        buildDetailsOrderDTO(g.getListDetailsOrder()),
        buildDetailsCartDTO(g.getListDetailsCarts()),
        buildReviewsDTO(g.getListReviews()),
        buildAuthorsDTO(g.getListAuthors()));
  }// buildGamesDTO

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

  public static AuthorsDTO builAuthorsDTO(Authors authors) {
    return new AuthorsDTO(
        authors.getId(),
        authors.getBiography(),
        authors.getCountry(),
        authors.getLastname(),
        authors.getName(),
        buildGamesDTOs(authors.getListGames()));
  }

  public static List<AuthorsDTO> buildAuthorsDTO(List<Authors> authorsList) {
    return authorsList.stream()
        .map(a -> new AuthorsDTO(
            a.getId(),
            a.getBiography(),
            a.getCountry(),
            a.getLastname(),
            a.getName(),
            buildGamesDTOs(a.getListGames())))
        .collect(Collectors.toList());
  }

  // public static List<CartsDTO> buildCartsDTO(List<Carts> c){

  // return c.stream()
  // .map(cart -> new CartsDTO(
  // cart.getId(),
  // cart.getCreatedAt(),
  // cart.getUpdatedAt(),
  // buildUsersDTO(cart.getUsersDTO())
  // ))
  // .collect(Collectors.toList());
  // }
  // public static CartsDTO buildCartsDTO(Carts c){

  // return new CartsDTO(
  // c.getId(),
  // c.getCreatedAt(),
  // c.getUpdatedAt(),
  // buildUsersDTO(c.getUsersDTO())
  // );
  // }

  // public static List<DetailsCartDTO> buildDetailsCartsDTO(List<DetailsCart>
  // dC){

  // return dC.stream()
  // .map(dCart -> new DetailsCartDTO(
  // dCart.getId(),
  // dCart.getPriceAtTime(),
  // dCart.getQuantity(),
  // buildGamesDTO(dCart.getGamesDTO()),
  // buildCartsDTO(dCart.getCartsDTO())
  // ))
  // .collect(Collectors.toList());
  // }
  // public static DetailsCartDTO buildDetailsCartsDTO(DetailsCart dC){

  // return new DetailsCartDTO(
  // dC.getId(),
  // dC.getPriceAtTime(),
  // dC.getQuantity(),
  // buildGamesDTO(dC.getGamesDTO()),
  // buildCartsDTO(dC.getCartsDTO())
  // );
  // }

}// class
