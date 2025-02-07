/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.ReviewsDTO;
import com.betagames.request.ReviewsRequest;

/**
 *
 * @author FabriniMarco
 */
public interface IReviewsService {
    List<ReviewsDTO> listByUserId(Integer idUser);

    void create (ReviewsRequest req) throws Exception;

    void update (ReviewsRequest req) throws Exception;

    // solo per admin 
    void delete(ReviewsRequest req) throws Exception;
}
