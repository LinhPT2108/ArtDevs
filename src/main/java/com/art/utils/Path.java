package com.art.utils;

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
}
