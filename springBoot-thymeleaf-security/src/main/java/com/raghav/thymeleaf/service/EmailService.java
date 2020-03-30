package com.raghav.thymeleaf.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void send(String email, String verificationUrl) {

		SimpleMailMessage message = new SimpleMailMessage();

		String userInfo = verificationUrl + "\n" + "Please click on link to verify your email address.";
		
		String subject = "Your One Last Step of a New Beginig!!! ";
		
		message.setTo(email);
		message.setSubject(subject);
		message.setText(userInfo);

		mailSender.send(message);
	}

}
