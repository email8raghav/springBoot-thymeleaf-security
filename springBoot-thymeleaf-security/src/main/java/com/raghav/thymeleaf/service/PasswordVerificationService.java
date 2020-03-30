package com.raghav.thymeleaf.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.raghav.thymeleaf.entity.PasswordResetTokenEntity;
import com.raghav.thymeleaf.entity.UserEntity;
import com.raghav.thymeleaf.exception.InvalidTokenException;
import com.raghav.thymeleaf.exception.UserNotFoundException;
import com.raghav.thymeleaf.repository.PasswordResetRepository;
import com.raghav.thymeleaf.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class PasswordVerificationService {

	@Autowired
	private Environment env;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordResetRepository passwordResetRepository;

	@Transactional
	public String generatePsswordResetToken(String email) {

		boolean status = userRepository.existsByEmail(email);

		if (status) {

			UserEntity userEntity = userRepository.findByEmail(email);
			/*
			 * Preparing password reset token
			 */
			String token = Jwts.builder().setSubject(userEntity.getPublicUserId())
					.setExpiration(new Date(
							System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
					.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();

			/*
			 * Preparing entity class to persist into db
			 * 
			 */
			PasswordResetTokenEntity resetTokenEntity = new PasswordResetTokenEntity(token);
			resetTokenEntity.setUserEntity(userEntity);
			/*
			 * Persisting to db
			 * 
			 */
			passwordResetRepository.save(resetTokenEntity);

			return token;

		} else {

			throw new UserNotFoundException("We are Sorry !!! This Email is not Found in our database !!!");
		}
	}

	public boolean isExpired(String token) {

		Claims claims = Jwts.parser().setSigningKey(env.getProperty("token.secret")).parseClaimsJws(token).getBody();

		Date tokenExpirationDate = claims.getExpiration();
		Date todayDate = new Date();
		return tokenExpirationDate.before(todayDate);
	}

	@Transactional
	public UserEntity verifyToken(String token) {

		PasswordResetTokenEntity tokenEntity = passwordResetRepository.findByToken(token);
		if (tokenEntity != null && !isExpired(tokenEntity.getToken())) {
			UserEntity userEntity = tokenEntity.getUserEntity();
			return userEntity;
		} else
			throw new InvalidTokenException("Invalid Token !!! Please Try again !!!");

	}


}
