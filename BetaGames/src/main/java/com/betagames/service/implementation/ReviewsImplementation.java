/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.betagames.service.implementation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betagames.dto.ReviewsDTO;
import com.betagames.model.Games;
import com.betagames.model.Reviews;
import com.betagames.model.Users;
import com.betagames.repository.IGamesRepository;
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

    public ReviewsImplementation(Logger log, IReviewsRepository reviewsRepository, IGamesRepository gamesRepository,
            IUsersRepository usersRepository) {
        this.log = log;
        this.reviewsRepository = reviewsRepository;
        this.gamesRepository = gamesRepository;
        this.usersRepository = usersRepository;
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
        if(!game.isPresent())
            throw new Exception("Game not found");
        
        Optional<Users> user = usersRepository.findById(req.getUsersId());
        if(!user.isPresent())
            throw new Exception("User not found");
        
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    /*
     * solo per admin
     */
    @Override
    public void delete(ReviewsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}// class
