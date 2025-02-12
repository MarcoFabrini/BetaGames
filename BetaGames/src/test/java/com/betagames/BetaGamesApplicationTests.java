package com.betagames;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;

@Suite
@SelectClasses({


	// RolesServiceTest.class,
	EditorsServiceTest.class,
	ReviewsServiceTest.class,
	UsersServiceTest.class
	// DetailsCartsServiceTest.class,
	// ICategoriesServiceTest.class
	//DetailsCartsControllerTest.class



})
@SpringBootTest(classes = BetaGamesApplication.class)
class BetaGamesApplicationTests {

	@Test
	void contextLoads() {
	}

}
