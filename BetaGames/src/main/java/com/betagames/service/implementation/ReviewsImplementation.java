/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.betagames.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betagames.dto.ReviewsDTO;
import com.betagames.request.ReviewsRequest;
import com.betagames.service.interfaces.IReviewsService;

/**
 *
 * @author FabriniMarco
 */
@Service
public class ReviewsImplementation implements IReviewsService{

    @Override
    public List<ReviewsDTO> list(Integer idUser) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'list'");
    }

    @Override
    public void create(ReviewsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void update(ReviewsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(ReviewsRequest req) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}// class
