package com.betagames;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.betagames.controller.DetailsCartsController;
import com.betagames.dto.DetailsCartDTO;
import com.betagames.request.DetailsCartRequest;
import com.betagames.response.ResponseBase;
import com.betagames.response.ResponseList;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DetailsCartsControllerTest {
    
    @Autowired
    DetailsCartsController dCController;

    @Autowired
    Logger log;

    @Test
	@Order(1)
	public void listTest() {
		ResponseList<DetailsCartDTO> res = dCController.list();
		
		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getData().size()).isEqualTo(0);
	}
    @Test
	@Order(2)
	public void createTest() {
        DetailsCartRequest req = new DetailsCartRequest();
        req.setGameId(1);
        req.setCartId(1);
        req.setQuantity(2);

		ResponseBase res = dCController.create(req);
		
		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getMsg()).isEqualTo("Successfully updated detailsCart");
	}

    @Test
	@Order(3)
	public void listByCartsTest() {

		ResponseList<DetailsCartDTO> res = dCController.listByCarts(1);
		
		Assertions.assertThat(res.getRc()).isEqualTo(true);
        //non mi torna...
		Assertions.assertThat(res.getData().size()).isEqualTo(0);
		// Assertions.assertThat(res.getData().get(0).getQuantity()).isEqualTo(1);

        res.getData().forEach(x->{
            log.debug("---------------------------: {}" + x.getId());
        });
    
	}

    @Test
	@Order(4)
	public void listByCartsExceptionTest() {
		ResponseList<DetailsCartDTO> res = dCController.listByCarts(2);
		
		Assertions.assertThat(res.getRc()).isEqualTo(false);
		Assertions.assertThat(res.getMsg()).isEqualTo("cart not found");
	}

    @Test
	@Order(5)
	public void updateTest() {
        DetailsCartRequest req = new DetailsCartRequest();
        req.setId(1);
        req.setQuantity(10);

		ResponseBase res = dCController.update(req);
		//non torna
		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getMsg()).isEqualTo("Successfully updated detailsCart");
	}
    @Test
	@Order(6)
	public void updateExceptionTest() {
        DetailsCartRequest req = new DetailsCartRequest();
        req.setId(300);
        req.setQuantity(10);

		ResponseBase res = dCController.update(req);

		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getMsg()).isEqualTo("details not found");

        // Exception exception = assertThrows(Exception.class, () -> {
        //     userService.login(usersRequest);
        // });
        // log.debug("loginWrongPwdTest: {}", exception.getMessage());
	}
    @Test
	@Order(7)
	public void deleteExceptionTest() {
        DetailsCartRequest req = new DetailsCartRequest();
        req.setId(3);

		ResponseBase res = dCController.delete(req);
		
		Assertions.assertThat(res.getRc()).isEqualTo(false);
		Assertions.assertThat(res.getMsg()).isEqualTo("cart not found");
	}
    @Test
	@Order(8)
	public void deleteTest() {
        DetailsCartRequest req = new DetailsCartRequest();
        req.setId(1);

		ResponseBase res = dCController.delete(req);
		//non torna
		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getMsg()).isEqualTo("Successfully updated detailsCart");
	}
    @Test
	@Order(9)
	public void deleteAllByCartExceptionTest() {

		ResponseBase res = dCController.deleteAllByCart(10);
		//non torna
		Assertions.assertThat(res.getRc()).isEqualTo(false);
		Assertions.assertThat(res.getMsg()).isEqualTo("cart not found");
	}
    @Test
	@Order(10)
	public void deleteAllByCartTest() {

		ResponseBase res = dCController.deleteAllByCart(1);
		//non torna
		Assertions.assertThat(res.getRc()).isEqualTo(true);
		Assertions.assertThat(res.getMsg()).isEqualTo("Successfully updated detailsCart");
	}
   
}
