package com.art.utils;

import java.util.Calendar;
import java.util.Date;

import com.art.dao.promotion.OrderDAO;

public class Path {
	public static final String BASE_PATH = "/rest";
	public static final String ADMIN_PATH = "/admin";

	public static String getQuery(String... keywords) {
		String query = "SELECT * FROM Product";
		if (keywords.length != 0) {
			for (int i = 0; i < keywords.length; i++) {
				String keyword = keywords[i].trim().replace(" ", "%%");
				if (i == 0) {
					query += " WHERE productName LIKE N'%"+keyword +"%'";
				} else {
					query += " OR productName LIKE N'%"+keyword +"%'";
				}
			}
		}
		return query;
	}
	
	public static Long getCountOrderByStatus(int numberDay,int delivery_status, OrderDAO orderDAO) {
		Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -numberDay);
        Date startDate = calendar.getTime();
        long count = 0;
       if(numberDay==0) {
    	   count = orderDAO.countOrdersByDeliveryStatusToday(delivery_status);
       }else {
    	   count = orderDAO.countOrdersByDeliveryStatusThisYear(delivery_status, startDate);
       }
        return count;
	}
}
