package com.hung.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.hung.dto.UserDTO;
import com.hung.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	public JavaMailSender javaMailSender;

	@Override
	public void sendMail(UserDTO user) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setSubject("Register Mail");
		mail.setText("You have successfully registered.");

		javaMailSender.send(mail);
	}
}
