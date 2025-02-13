package com.betagames;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import com.betagames.controller.EditorsControllerTest;
import com.betagames.controller.ReviewsControllerTest;
import com.betagames.controller.UsersControllerTest;

import com.betagames.controller.DetailsCartsControllerTest;

@Suite
@SelectClasses({

	RolesServiceTest.class,
  RolesControllerTest.class,
	EditorsServiceTest.class,
	ReviewsServiceTest.class,
	UsersServiceTest.class,
	EditorsControllerTest.class,
	ReviewsControllerTest.class,
	UsersControllerTest.class,
	DetailsCartsServiceTest.class,
	DetailsCartsControllerTest.class
	// ICategoriesServiceTest.class,
	// IAuthorsServiceTest.class,
	// IGamesServicesTest.class

})
@SpringBootTest(classes = BetaGamesApplication.class)
class BetaGamesApplicationTests {
	@Test
	void contextLoads() {
	}
}// class
