package com.betagames.service.implementation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.ReviewsDTO;
import com.betagames.model.DetailsOrder;
import com.betagames.model.Games;
import com.betagames.model.Orders;
import com.betagames.model.Reviews;
import com.betagames.model.Users;
import com.betagames.repository.IDetailsOrderRepository;
import com.betagames.repository.IGamesRepository;
import com.betagames.repository.IOrdersRepository;
import com.betagames.repository.IReviewsRepository;
import com.betagames.repository.IUsersRepository;
import com.betagames.request.ReviewsRequest;
import com.betagames.service.interfaces.IReviewsService;
import static com.betagames.utility.Utilities.buildReviewsDTO;

/**
 *
 * @author FabriniMarco
 */
@Service
public class ReviewsImplementation implements IReviewsService {
    private final Logger log;
    private final IReviewsRepository reviewsRepository;
    private final IGamesRepository gamesRepository;
    private final IUsersRepository usersRepository;
    private final IDetailsOrderRepository detailsOrderRepository;

    public ReviewsImplementation(Logger log, IReviewsRepository reviewsRepository, IGamesRepository gamesRepository,
			IUsersRepository usersRepository, IDetailsOrderRepository detailsOrderRepository) {
		super();
		this.log = log;
		this.reviewsRepository = reviewsRepository;
		this.gamesRepository = gamesRepository;
		this.usersRepository = usersRepository;
		this.detailsOrderRepository = detailsOrderRepository;
	} 

	@Override
    public List<ReviewsDTO> listByUserId(Integer idUser) {
        List<Reviews> reviews = reviewsRepository.findByUserId(idUser);
        return buildReviewsDTO(reviews);
    }// listById

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ReviewsRequest req) throws Exception {
        Date now = new Date();
        Optional<Games> game = gamesRepository.findById(req.getGameId());
        if (!game.isPresent())
            throw new Exception("Game not found");

        Optional<Users> user = usersRepository.findById(req.getUsersId());
        if (!user.isPresent())
            throw new Exception("User not found");
        
        Long detailsOrder = detailsOrderRepository.findOrderForReview(user.get().getId(), game.get().getId());
        if(detailsOrder == 0)
        	throw new Exception("User has not purchased this game and cannot leave a review");
        
        Optional<Reviews> existingReview = reviewsRepository.findByUserIdAndGameId(user.get().getId(), game.get().getId());
        if (existingReview.isPresent())
            throw new Exception("User has already reviewed this game");

        Reviews review = new Reviews();
        review.setGame(game.get());
        review.setUser(user.get());
        review.setScore(req.getScore());
        review.setCreatedAt(now);
        review.setDescription(req.getDescription());

        reviewsRepository.save(review);
    }// create

    @Override
    public void update(ReviewsRequest req) throws Exception {
        Date now = new Date();

        Optional<Reviews> review = reviewsRepository.findById(req.getId());
        if (!review.isPresent())
            throw new Exception("Review not found");

        Optional<Games> game = gamesRepository.findById(req.getGameId());
        if (!game.isPresent())
            throw new Exception("Game not found");

        Optional<Users> user = usersRepository.findById(req.getUsersId());
        if (!user.isPresent())
            throw new Exception("User not found");
        
        Long detailsOrder = detailsOrderRepository.findOrderForReview(user.get().getId(), game.get().getId());
        if(detailsOrder == 0)
        	throw new Exception("User has not purchased this game and cannot leave a review");

        review.get().setCreatedAt(now);
        review.get().setScore(req.getScore());
        review.get().setDescription(req.getDescription());

        reviewsRepository.save(review.get());
    }// update

    /*
     * solo per admin
     */
    @Override
    public void delete(ReviewsRequest req) throws Exception {
        Optional<Reviews> review = reviewsRepository.findById(req.getId());
        if (!review.isPresent())
            throw new Exception("Review not found");

        reviewsRepository.delete(review.get());
    }// delete

}// class
