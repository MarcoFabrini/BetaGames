package com.betagames;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;

import com.betagames.controller.DetailsCartsControllerTest;

@Suite
@SelectClasses({
	RolesServiceTest.class,
	UsersServiceTest.class,
	CartsServiceTest.class,
	PayCardsServiceTest.class,

	EditorsServiceTest.class,
	IGamesServicesTest.class,
	ICategoriesServiceTest.class,
	IAuthorsServiceTest.class,
	
	ReviewsServiceTest.class,

	DetailsCartsServiceTest.class,
	DetailsCartsControllerTest.class,

	OrdersServiceTes.class,

})
@SpringBootTest(classes = BetaGamesApplication.class)
class BetaGamesApplicationTests {
	@Test
	void contextLoads() {
	}
}//class
