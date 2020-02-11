package com.hung.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {
	public static String covertToString(String value) {
	      try {
	            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
	            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	            return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D').toLowerCase();
	     } catch (Exception ex) {
	            ex.printStackTrace();
	      }
	      return null;
	}
}
