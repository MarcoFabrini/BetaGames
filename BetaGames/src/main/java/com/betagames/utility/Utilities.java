package com.betagames.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.betagames.dto.OrdersDTO;
import com.betagames.model.Orders;

public class Utilities {
    
    public static List<OrdersDTO> buildOrdersDTO(List<Orders> listOrders){
        return listOrders.stream()
                        .map(order -> new OrdersDTO(order.getId(), order.getTotalAmmount(),order.getOrderStatus(), order.getCreatedAt(), order.getUpdatedAt(), 
                                                    null, null, null)) //settati a null perchè mancano i buildDTO
                        .collect(Collectors.toList());
    }
    private final static String PATTERN_DATE = "dd/MM/yyyy";
	public static Date convertStringToDate(String dataString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_DATE, Locale.ITALY);
		return formatter.parse(dataString);
	}
}
