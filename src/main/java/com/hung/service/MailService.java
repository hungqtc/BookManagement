package com.hung.service;

import com.hung.dto.UserDTO;

public interface MailService {
	void sendMail(UserDTO user);
}
