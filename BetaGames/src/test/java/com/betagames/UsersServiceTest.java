package com.betagames;

import java.util.List;

import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.betagames.dto.UsersDTO;
import com.betagames.request.RolesRequest;
import com.betagames.request.UsersRequest;
import com.betagames.service.interfaces.IRolesService;
import com.betagames.service.interfaces.IUsersService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UsersServiceTest {
    @Autowired
    Logger log;
    @Autowired
    IRolesService rolesService;
    @Autowired
    IUsersService userService;

    private RolesRequest globalRolesRequest;
    private UsersRequest globalUserRequest;

    private void roles() throws Exception{
        globalRolesRequest = new RolesRequest();
        globalRolesRequest.setName("user");
        rolesService.create(globalRolesRequest);
    }// roles

    private void user() throws Exception{
        roles();
        globalUserRequest = new UsersRequest();
        globalUserRequest.setUsername("userTest");
        globalUserRequest.setPwd("userTest");
        globalUserRequest.setEmail("userTest@example.com");
        globalUserRequest.setActive(true);
        globalUserRequest.setRoleId(1);
        userService.createUser(globalUserRequest);
    }// user

    @Test
    @Order(1)
    public void createUsersTest() throws Exception {
       user();

        List<UsersDTO> listUsersDTO = userService.list();

        UsersDTO foundUsers = listUsersDTO.stream()
                .filter(u -> "userTest".equals(u.getUsername()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Review not found"));

        Assertions.assertThat(foundUsers.getId()).isEqualTo(1);
        Assertions.assertThat(listUsersDTO.size()).isEqualTo(1);

        listUsersDTO.forEach(r -> log.debug("TEST create UsersTest: " + r.toString()));
    }// createUsersTest

    @Test
    @Order(2)
    public void createUsersNotRoleIdTest() throws Exception {
        UsersRequest usersRequest = new UsersRequest();
        usersRequest.setId(1);
        usersRequest.setRoleId(100);

        assertThrows(Exception.class, () -> {
            userService.createUser(usersRequest);
        });
    }// createReviewsNotUserIdTest

    // @Test
    // @Order(3)
    // public void createReviewsNotGameIdTest() throws Exception {
    //     ReviewsRequest reviewsRequest = new ReviewsRequest();
    //     reviewsRequest.setId(1);
    //     reviewsRequest.setUsersId(1);
    //     reviewsRequest.setGameId(100);

    //     assertThrows(Exception.class, () -> {
    //         reviewsService.create(reviewsRequest);
    //     });
    // }// createReviewsNotGameIdTest
}
//     @Test
//     @Order(4)
//     public void updateReviewsTest() throws Exception {
//         ReviewsRequest reviewsRequest = new ReviewsRequest();
//         reviewsRequest.setId(1);
//         reviewsRequest.setScore(0);
//         reviewsRequest.setDescription("description updated");
//         reviewsRequest.setCreatedAt(now);
//         reviewsRequest.setUsersId(1);
//         reviewsRequest.setGameId(1);

//         reviewsService.update(reviewsRequest);

//         List<ReviewsDTO> listReviewsDTO = reviewsService.listByUserId(1);

//         ReviewsDTO foundReview = listReviewsDTO.stream()
//                 .filter(e -> "description updated".equals(e.getDescription()))
//                 .findFirst()
//                 .orElseThrow(() -> new AssertionError("Review not found"));

//         Assertions.assertThat(foundReview.getId()).isEqualTo(1);
//         Assertions.assertThat(listReviewsDTO.size()).isEqualTo(1);

//         listReviewsDTO.forEach(r -> log.debug("TEST update ReviewsTest: " + r.toString()));
//     }// updateReviewsTest

//     @Test
//     @Order(5)
//     public void updateReviewsNotIdTest() throws Exception {
//         ReviewsRequest reviewsRequest = new ReviewsRequest();
//         reviewsRequest.setId(100);

//         assertThrows(Exception.class, () -> {
//             reviewsService.update(reviewsRequest);
//         });
//     }// updateReviewsNotIdTest

//     @Test
//     @Order(6)
//     public void updateReviewsNotUserIdTest() throws Exception {
//         ReviewsRequest reviewsRequest = new ReviewsRequest();
//         reviewsRequest.setId(1);
//         reviewsRequest.setUsersId(100);
//         reviewsRequest.setGameId(1);

//         assertThrows(Exception.class, () -> {
//             reviewsService.update(reviewsRequest);
//         });
//     }// updateReviewsNotIdTest

//     @Test
//     @Order(7)
//     public void updateReviewsNotGameIdTest() throws Exception {
//         ReviewsRequest reviewsRequest = new ReviewsRequest();
//         reviewsRequest.setId(1);
//         reviewsRequest.setUsersId(1);
//         reviewsRequest.setGameId(100);

//         assertThrows(Exception.class, () -> {
//             reviewsService.update(reviewsRequest);
//         });
//     }// updateReviewsNotIdTest

//     @Test
//     @Order(8)
//     public void deleteReviewsNotIdTest() throws Exception {
//         ReviewsRequest reviewsRequest = new ReviewsRequest();
//         reviewsRequest.setId(100);

//         assertThrows(Exception.class, () -> {
//             reviewsService.delete(reviewsRequest);
//         });
//     }// deleteReviewsNotIdTest

//     @Test
//     @Order(9)
//     public void deleteReviewsTest() throws Exception {
//         ReviewsRequest reviewsRequest = new ReviewsRequest();
//         reviewsRequest.setId(1);

//         reviewsService.delete(reviewsRequest);

//         List<ReviewsDTO> listReviewsDTO = reviewsService.listByUserId(1);

//         Assertions.assertThat(listReviewsDTO.size()).isEqualTo(0);

//         listReviewsDTO.forEach(r -> log.debug("TEST delete ReviewsTest: " + r.toString()));
//     }// deleteReviewsTest

// }// class
