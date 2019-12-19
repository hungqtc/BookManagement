package com.hung.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hung.config.security.CustomUserDetails;

public class SecurityUtil {
	public static CustomUserDetails getPrincipal() {
		String anonymousUser = "anonymousUser";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal().equals(anonymousUser)) {
            return null;
        }
		
		return (CustomUserDetails) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();

	}

	public static String checkRoleUser(String role) {
	
		if (role.contains("[ROLE_ADMIN]") == true) {
			return "ADMIN";
		} else if (role.contains("[ROLE_USER]") == true) {
			return "USER";
		}
		return null;

	}
}
