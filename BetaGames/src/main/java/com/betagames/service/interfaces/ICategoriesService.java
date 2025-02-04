package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.CategoriesDTO;
import com.betagames.request.CategoriesRequest;

/**
 *
 * @author Cristhian Guerrero
 */
public interface ICategoriesService {

    List<CategoriesDTO> list() throws Exception;

    List<CategoriesDTO> searchByTyping(Integer id, String name, String lastname, String country, String biography) throws Exception;

    void create(CategoriesRequest req) throws Exception;

    void update(CategoriesRequest req) throws Exception;

    void delete(CategoriesRequest req) throws Exception;

}// interfaces
