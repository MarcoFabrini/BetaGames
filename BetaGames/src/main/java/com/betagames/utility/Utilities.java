package com.betagames.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.betagames.dto.AuthorsDTO;
import com.betagames.dto.CartsDTO;
import com.betagames.dto.CategoriesDTO;
import com.betagames.dto.DetailsCartDTO;
import com.betagames.dto.DetailsOrderDTO;
import com.betagames.dto.DetailsShippingDTO;
import com.betagames.dto.EditorsDTO;
import com.betagames.dto.GamesDTO;
import com.betagames.dto.OrdersDTO;
import com.betagames.dto.PayCardsDTO;
import com.betagames.dto.ReviewsDTO;
import com.betagames.dto.RolesDTO;
import com.betagames.dto.UsersDTO;
import com.betagames.model.Authors;
import com.betagames.model.Carts;
import com.betagames.model.Categories;
import com.betagames.model.DetailsCart;
import com.betagames.model.DetailsOrder;
import com.betagames.model.DetailsShipping;
import com.betagames.model.Editors;
import com.betagames.model.Games;
import com.betagames.model.Orders;
import com.betagames.model.PayCards;
import com.betagames.model.Reviews;
import com.betagames.model.Roles;
import com.betagames.model.Users;

public class Utilities {
  private final static String PATTERN_DATE = "dd/MM/yyyy";

  public static Date convertStringToDate(String dataString) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DATE, Locale.ITALY);
    return formatter.parse(dataString);
  }

  public static String convertDateToString(Date date) throws ParseException{
    DateFormat formatter = new SimpleDateFormat(PATTERN_DATE, Locale.ITALY);
		return formatter.format(date);
  }

  public static AuthorsDTO builAuthorsDTO(Authors authors) {
    return new AuthorsDTO(
        authors.getId(),
        authors.getBiography(),
        authors.getCountry(),
        authors.getLastname(),
        authors.getName()
        );
  }

  public static List<AuthorsDTO> buildAuthorsDTO(List<Authors> authorsList) {
    return authorsList.stream()
        .map(a -> new AuthorsDTO(
            a.getId(),
            a.getBiography(),
            a.getCountry(),
            a.getLastname(),
            a.getName()))
        .collect(Collectors.toList());
  }

  public static List<CartsDTO> buildCartsDTO(List<Carts> c) {
    return c.stream()
        .map(cart -> new CartsDTO(
            cart.getId(),
            cart.getCreatedAt(),
            cart.getUpdatedAt(),
            buildDetailsCartsDTO(cart.getListDetailsCart())
          ))
        .collect(Collectors.toList());
  }

  public static CartsDTO buildCartsDTO(Carts c) {
    return new CartsDTO(
        c.getId(),
        c.getCreatedAt(),
        c.getUpdatedAt(),
        buildDetailsCartsDTO(c.getListDetailsCart()));
  }

  public static CategoriesDTO buildCategoriesDTO(Categories c) {
    return new CategoriesDTO(
        c.getId(),
        c.getName());
  }

  public static List<CategoriesDTO> buildCategoriesDTO(List<Categories> ca) {
    return ca.stream()
        .map(c -> new CategoriesDTO(
            c.getId(),
            c.getName()))
        .collect(Collectors.toList());
  }

  public static List<DetailsCartDTO> buildDetailsCartsDTO(List<DetailsCart> dC) {
    return dC.stream()
        .map(dCart -> new DetailsCartDTO(
            dCart.getId(),
            dCart.getQuantity(),
            buildGamesDTO(dCart.getGame())
        ))
        .collect(Collectors.toList());
  }

  public static DetailsCartDTO buildDetailsCartsDTO(DetailsCart dC) {
    return new DetailsCartDTO(
        dC.getId(),
        dC.getQuantity(),
        buildGamesDTO(dC.getGame()
        ));
  }

  // builder per il singolo DetailsOrderDTO
  public final static DetailsOrderDTO buildDetailsOrderDTO(DetailsOrder d) {
    return new DetailsOrderDTO(
        d.getId(),
        d.getQuantity(),
        d.getPriceAtTime(),
        buildGamesDTO(d.getGame()));
  }

  // builder per farsi restituire la lista DetailsOrderDTO
  public final static List<DetailsOrderDTO> buildDetailsOrderDTO(List<DetailsOrder> listDetailsOrder) {
    return listDetailsOrder.stream()
        .map(detail -> new DetailsOrderDTO(
            detail.getId(),
            detail.getQuantity(),
            detail.getPriceAtTime(),
            buildGamesDTO(detail.getGame())))
        .collect(Collectors.toList());
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
        buildAuthorsDTO(g.getListAuthors()),
        buildCategoriesDTO(g.getListCategory()),
        buildReviewsDTO(g.getListReviews()));
  }// buildGamesDTO

  public static List<GamesDTO> buildGamesDTO(List<Games> gs) {
    return gs.stream()
        .map(g -> new GamesDTO(
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
          buildAuthorsDTO(g.getListAuthors()),
          buildCategoriesDTO(g.getListCategory()),
          buildReviewsDTO(g.getListReviews())))
        .collect(Collectors.toList());
  }// List buildGamesDTO

  // builder per farsi restituire un OrdersDTO
  public final static OrdersDTO buildOrdersDTO(Orders o) {
    return new OrdersDTO(
        o.getId(),
        o.getTotalAmmount(),
        o.getOrderStatus(),
        o.getCreatedAt(),
        o.getUpdatedAt(),
        buildDetailsOrderDTO(o.getListDetailsOrder()),
        buildPayCardsDTO(o.getPayCard()));
  }

  // builder per farsi restituire la lista di OrdersDTO
  public final static List<OrdersDTO> buildOrdersDTO(List<Orders> listOrders) {
    return listOrders.stream()
        .map(order -> new OrdersDTO(
            order.getId(),
            order.getTotalAmmount(),
            order.getOrderStatus(),
            order.getCreatedAt(),
            order.getUpdatedAt(),
            buildDetailsOrderDTO(order.getListDetailsOrder()),
            buildPayCardsDTO(order.getPayCard())))
        .collect(Collectors.toList());
  }

  public static PayCardsDTO buildPayCardsDTO(PayCards p) {
    return new PayCardsDTO(
        p.getId(),
        p.getCardNumber(),
        p.getCardHolderName(),
        p.getExpirationDate(),
        p.getCvv(),
        p.getBillingAddress(),
        p.getCreatedAt(),
        p.getUpdatedAt(),
        p.getActive());
  }// PayCardsDTO

  public static List<PayCardsDTO> buildPayCardsDTO(List<PayCards> p) {
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
            ps.getActive()
          ))
        .collect(Collectors.toList());
  }// List buildPayCardsDTO

  public static ReviewsDTO buildReviewsDTO(Reviews r) {
    return new ReviewsDTO(
        r.getId(),
        r.getScore(),
        r.getDescription(),
        r.getCreatedAt()
        );
  }// buildReviewsDTO

  public static List<ReviewsDTO> buildReviewsDTO(List<Reviews> r) {
    return r.stream()
        .map(re -> new ReviewsDTO(
            re.getId(),
            re.getScore(),
            re.getDescription(),
            re.getCreatedAt()
            ))
        .collect(Collectors.toList());
  }// buildReviewsDTO

  // build per Il singolo RolesDTO
  public final static RolesDTO buildRolesDTO(Roles r) {
    return new RolesDTO(
        r.getId(),
        r.getName()
      );
  }

  // builder per farsi restituire la lista di RolesDTO
  public final static List<RolesDTO> buildRolesDTO(List<Roles> listRoles) {
    return listRoles.stream()
        .map(role -> new RolesDTO(
            role.getId(),
            role.getName()
           ))
        .collect(Collectors.toList());
  }
  
  public final static DetailsShippingDTO buildDetailsShipping(DetailsShipping d) {
	  return new DetailsShippingDTO(
			  d.getId(),
			  d.getName(),
			  d.getLastname(),
			  d.getCountry(),
			  d.getStateRegion(),
			  d.getCap(),
			  d.getCity(),
			  d.getAddress(),
			  d.getActive()
			  );
  }// buildDetailsShipping
  
  public final static List<DetailsShippingDTO> buildDetailsShipping(List<DetailsShipping> listD) {
	  return listD.stream()
			  .map(d -> new DetailsShippingDTO(
					  d.getId(),
					  d.getName(),
					  d.getLastname(),
					  d.getCountry(),
					  d.getStateRegion(),
					  d.getCap(),
					  d.getCity(),
					  d.getAddress(),
					  d.getActive()
					  ))
			  .collect(Collectors.toList());
  }//buildDetailsShipping

  public static UsersDTO buildUsersDTO(Users u) {
    return new UsersDTO(
        u.getId(),
        u.getUsername(),
        u.getEmail(),
        buildOrdersDTO(u.getListOrders()),
        buildCartsDTO(u.getCart()),
        buildPayCardsDTO(u.getListPayCards()),
        buildRolesDTO(u.getRole()),
        buildDetailsShipping(u.getListDetailsShippings()),
        u.getActive()
        );
  }// buildUsersDTO

  public static List<UsersDTO> buildUsersDTO(List<Users> u) {
    return u.stream()
        .map(us -> new UsersDTO(
            us.getId(),
            us.getUsername(),
            us.getEmail(),
            buildOrdersDTO(us.getListOrders()),
            buildCartsDTO(us.getCart()),
            buildPayCardsDTO(us.getListPayCards()),
            buildRolesDTO(us.getRole()),
            buildDetailsShipping(us.getListDetailsShippings()),
            us.getActive()
            ))
        .collect(Collectors.toList());
  }// buildUsersDTO

}// class
