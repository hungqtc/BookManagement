package com.hung.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.hung.security.CustomUserDetails;

public class SecurityUtil {
	public static CustomUserDetails getPrincipal() {
		CustomUserDetails myUser =  (CustomUserDetails) (SecurityContextHolder.getContext()).getAuthentication()
				.getPrincipal();
		return myUser;
	}
	
	public static String checkRoleUser(String role) {
		
		if (role.concat("[ROLE_ADMIN]") != null) {
			return "ADMIN";
		} 
		else if (role.concat("[ROLE_USER]") != null) {
			return "USER";
		} 
		
		return null;
		
	}
}
