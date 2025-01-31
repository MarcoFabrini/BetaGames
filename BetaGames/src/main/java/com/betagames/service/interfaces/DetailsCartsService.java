package com.betagames.service.interfaces;

import com.betagames.request.DetailsCartRequest;

public interface DetailsCartsService {

    void create(DetailsCartRequest req) throws Exception;
    void update(DetailsCartRequest req) throws Exception;
    void delete(DetailsCartRequest req) throws Exception;
}
