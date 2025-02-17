package com.betagames;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import com.betagames.controller.AuthorsControllerTest;
import com.betagames.controller.CartsControllerTest;
import com.betagames.controller.CategoriesControllerTest;
import com.betagames.controller.DetailsCartsControllerTest;
import com.betagames.controller.DetailsShippingControllerTest;
import com.betagames.controller.EditorsControllerTest;
import com.betagames.controller.GamesControllerTest;
import com.betagames.controller.OrderControllerTest;
import com.betagames.controller.PayCardsControllerTest;
import com.betagames.controller.ReviewsControllerTest;
import com.betagames.controller.RolesControllerTest;
import com.betagames.controller.UsersControllerTest;
import com.betagames.service.AuthorsServiceTest;
import com.betagames.service.CartsServiceTest;
import com.betagames.service.CategoriesServiceTest;
import com.betagames.service.DetailsCartsServiceTest;
import com.betagames.service.DetailsShippingServiceTest;
import com.betagames.service.EditorsServiceTest;
import com.betagames.service.GamesServicesTest;
import com.betagames.service.OrdersServiceTest;
import com.betagames.service.PayCardsServiceTest;
import com.betagames.service.ReviewsServiceTest;
import com.betagames.service.RolesServiceTest;
import com.betagames.service.UsersServiceTest;

@Suite
@SelectClasses({
	RolesServiceTest.class,
  	RolesControllerTest.class,
	EditorsServiceTest.class,
	ReviewsServiceTest.class,
	UsersServiceTest.class,
	CategoriesServiceTest.class,
	AuthorsServiceTest.class,
	GamesServicesTest.class,
	CategoriesControllerTest.class,
	AuthorsControllerTest.class,
	EditorsControllerTest.class,
	ReviewsControllerTest.class,
	UsersControllerTest.class,
	DetailsCartsServiceTest.class,
	DetailsCartsControllerTest.class,
	PayCardsControllerTest.class,
	PayCardsServiceTest.class,
	GamesControllerTest.class,
	OrderControllerTest.class,
	OrdersServiceTest.class,
	CartsControllerTest.class,
	CartsServiceTest.class,
	DetailsShippingServiceTest.class,
	DetailsShippingControllerTest.class
})
@SpringBootTest(classes = BetaGamesApplication.class)
class BetaGamesApplicationTests {
	@Test
	void contextLoads() {
	}
}// class
