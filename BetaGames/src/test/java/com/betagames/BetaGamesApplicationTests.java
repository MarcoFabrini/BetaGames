package com.betagames;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Suite
@SelectClasses({
	EditorsServiceTest.class,
	RolesServiceTest.class
})

@SpringBootTest
@ActiveProfiles(value="test")
class BetaGamesApplicationTests {

	@Test
	void contextLoads() {
	}

}
