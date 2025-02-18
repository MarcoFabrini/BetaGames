package com.betagames.service.interfaces;

import java.util.List;

import com.betagames.dto.DetailsShippingDTO;
import com.betagames.request.DetailsShippingRequest;

public interface IDetailsShippingService {
	
	List<DetailsShippingDTO> list(DetailsShippingRequest req)  throws Exception;
	
	void create(DetailsShippingRequest req) throws Exception;
	
	void update(DetailsShippingRequest req) throws Exception;

	void delete(DetailsShippingRequest req) throws Exception;
}// interface 
