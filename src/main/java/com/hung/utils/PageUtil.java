package com.hung.utils;

public class PageUtil {
	public static int totalPage(int totalItem , int limit) {
		return (int) Math.ceil((double) totalItem / limit);
	}
}
