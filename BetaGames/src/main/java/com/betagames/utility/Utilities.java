package com.betagames.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.betagames.dto.EditorsDTO;
import com.betagames.dto.OrdersDTO;

import com.betagames.dto.ReviewsDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.model.Editors;
import com.betagames.model.Orders;
import com.betagames.model.Reviews;
import com.betagames.model.Users;

import com.betagames.dto.PayCardsDTO;
import com.betagames.model.Orders;
import com.betagames.model.PayCards;

import com.betagames.dto.RolesDTO;
import com.betagames.model.Orders;
import com.betagames.model.Roles;
import com.betagames.dto.DetailsOrderDTO;

import com.betagames.dto.EditorsDTO;
import com.betagames.model.DetailsOrder;
import com.betagames.model.Editors;

public class Utilities {

  private final static String PATTERN_DATE = "dd/MM/yyyy";
  
  //builder per farsi restituire la lista di OrdersDTO
  public final static List<OrdersDTO> buildOrdersDTO(List<Orders> listOrders){
    return listOrders.stream()
              .map(order -> new OrdersDTO(order.getId(), order.getTotalAmmount(),order.getOrderStatus(), order.getCreatedAt(), order.getUpdatedAt(), 
                                                    null, null, null)) //settati a null perchè mancano i buildDTO
              .collect(Collectors.toList());
  }
  //builder per farsi restituire la lista di RolesDTO
  public final static List<RolesDTO> buildRolesDTO(List<Roles> listRoles){
    return listRoles.stream()
              .map(role -> new RolesDTO(role.getId(), role.getName(), null)) //settato a null perchè manca buildUsersDTO
              .collect(Collectors.toList());
  }
  //builder per farsi restituire la lista DetailsOrderDTO
  public final static List<DetailsOrderDTO> buildDetailsOrderDTO(List<DetailsOrder> listDetailsOrder){
    return listDetailsOrder.stream()
              .map(detail -> new DetailsOrderDTO(detail.getId(),detail.getQuantity(),detail.getPriceAtTime(), 
                                  buildOrdersDTO(detail.getOrder()),null))
              .collect(Collectors.toList());
  }
  //builder per farsi restituire un OrdersDTO
  public final static OrdersDTO buildOrdersDTO(Orders o){
    return new OrdersDTO(o.getId(), o.getTotalAmmount(), o.getOrderStatus(), 
                        o.getCreatedAt(), o.getUpdatedAt(), null, null, null);
  }
  //build per Il singolo RolesDTO
  public final static RolesDTO buildRolesDTO(Roles r){
    return new RolesDTO(r.getId(),r.getName(),null);
  }
  //builder per il singolo DetailsOrderDTO
  public final static DetailsOrderDTO buildDetailsOrderDTO(DetailsOrderDTO d){
    return new DetailsOrderDTO(d.getId(),d.getQuantity(),d.getPriceAtTime(),d.getOrderDTO(),null);
  }
    public static EditorsDTO buildEditorsDTO(Editors e) {
        return new EditorsDTO(
                e.getId(),
                e.getName(),
                e.getWebsite());
    }// buildEditorsDTO

  public static List<ReviewsDTO> buildReviewsDTO(List<Reviews> r) {
    return r.stream()
        .map(re -> new ReviewsDTO(
            re.getId(),
            re.getScore(),
            re.getDescription(),
            re.getCreatedAt(),
            buildUsersDTO(re.getUser()),
            null))
            // buildGamesDTO(re.getGame())))
        .collect(Collectors.toList());
  }// buildReviewsDTO
  
  public static ReviewsDTO buildReviewsDTO(Reviews r) {
    return new ReviewsDTO(
        r.getId(),
        r.getScore(),
        r.getDescription(),
        r.getCreatedAt(),
        buildUsersDTO(r.getUser()),
        null);
        // buildGamesDTO(r.getGame()));
  }// buildReviewsDTO

  public static UsersDTO buildUsersDTO(Users u) {
    return new UsersDTO(
        u.getId(),
        u.getUsername(),
        u.getEmail(),
        u.getPwd(),
        buildReviewsDTO(u.getListReviews()),
        buildOrdersDTO(u.getListOrders()),
        null,
        null
        );
        // buildPayCardsDTO(u.getListPayCards()));
  }// buildUsersDTO

  public static List<UsersDTO> buildUsersDTO(List<Users> u) {
    return u.stream()
        .map(us -> new UsersDTO(
            us.getId(),
            us.getUsername(),
            us.getEmail(),
            us.getPwd(),
            buildReviewsDTO(us.getListReviews()),
            buildOrdersDTO(us.getListOrders()),
            null,
            // buildRoleDTO(us.getRole())
            null))
            // buildPayCardsDTO(us.getListPayCards())))
        .collect(Collectors.toList());
  }// buildUsersDTO

  // public static List<GamesDTO> buildAbbonamentoDTO(List<Games> games) {
  // return games.stream()
  // .map(a -> new GamesDTO(
  // a.getId(),
  // a.getName(),
  // a.getDate(),
  // a.getMinGameTime(),
  // a.getMaxGameTime(),
  // a.getMinPlayerNumber(),
  // a.getMaxPlayerNumber(),
  // a.getMinAge(),
  // a.getDescription(),
  // a.getStockQuantity(),
  // a.getPrice(),
  // a.getListAuthors()))
  // .collect(Collectors.toList());
  // }

  public static List<OrdersDTO> buildOrdersDTO(List<Orders> listOrders) {
    return listOrders.stream()
        .map(order -> new OrdersDTO(order.getId(), order.getTotalAmmount(), order.getOrderStatus(),
            order.getCreatedAt(), order.getUpdatedAt(),
            null, null, null)) // settati a null perchè mancano i buildDTO
        .collect(Collectors.toList());
  }


    public static GamesDTO buildGamesDTO(Games g){
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
                  buildAuthorsDTO(g.getListAuthors())
       );
    }//buildGamesDTO

    public static List<GamesDTO> buildGamesDTO(List<Games> g){
      return g.stream()
              .map(gs -> new GamesDTO(
                gs.getId(),
                gs.getName(),
                gs.getDate(),
                gs.getMinGameTime(),
                gs.getMaxGameTime(),
                gs.getMinPlayerNumber(),
                gs.getMaxPlayerNumber(),
                gs.getMinAge(),
                gs.getDescription(),
                gs.getStockQuantity(),
                gs.getPrice(),
                buildEditorsDTO(gs.getEditor()),
                buildCategoryDTO(gs.getListCategory()),
                buildDetailsOrderDTO(gs.getListDetailsOrder()),
                buildDetailsCartDTO(gs.getListDetailsCarts()),
                buildReviewsDTO(gs.getListReviews()),
                buildAuthorsDTO(gs.getListAuthors())
                ))
                .collect(Collectors.toList());
    }//List buildGamesDTO

    public static PayCardsDTO buildPayCardsDTO(PayCards p){
      return new PayCardsDTO(
                  p.getId(),
                  p.getCardNumber(),
                  p.getCardHolderName(),
                  p.getExpirationDate(),
                  p.getCvv(),
                  p.getBillingAddress(),
                  p.getCreatedAt(),
                  p.getUpdatedAt(),
                  buildUsersDTO(p.getUser()),
                  buildOrdersDTO(p.getOrder())
      );
    }//PayCardsDTO

    public static List<PayCardsDTO> buildPayCardsDTO(List<PayCards> p){
      return p.stream()
              .map(ps -> new PayCardsDTO(
                ps.getId(),
                ps.getCardNumber(),
                ps.getCardHolderName(),
                ps.getExpirationDate(),
                ps.getCvv(),
                ps.getBillingAddress(),
                ps.getCreatedAt(),
                ps.getUpdatedAt(),
                buildUsersDTO(ps.getUser()),
                buildOrdersDTO(ps.getOrder())
              ))
              .collect(Collectors.toList());
    }//List buildPayCardsDTO
  
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



    

  public static Date convertStringToDate(String dataString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DATE, Locale.ITALY);
		return formatter.parse(dataString);
	}

}// class
