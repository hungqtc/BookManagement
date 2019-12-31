package com.hung.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hung.config.security.CustomUserDetails;

public class SecurityUtil {
	public static CustomUserDetails getPrincipal() {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return null;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ("anonymousUser".equals(authentication.getPrincipal()) || authentication == null
				|| !authentication.isAuthenticated()) {
			return null;
		}
		return (CustomUserDetails) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
	}

	public static String checkRoleUser(String role) {
		if (role.contains("[ROLE_ADMIN]")) {
			return "ADMIN";
		} else if (role.contains("[ROLE_USER]")) {
			return "USER";
		}
		return null;
	}
}
