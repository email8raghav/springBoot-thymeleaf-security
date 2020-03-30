package com.raghav.thymeleaf.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.raghav.thymeleaf.entity.UserEntity;
import com.raghav.thymeleaf.model.request.SignUpUserRequestModel;


public interface UserService extends UserDetailsService {
	
	public UserEntity createNewUser(SignUpUserRequestModel signUpUserRequestModel);
	
	public UserEntity getUserByEmail(String email);

	public UserEntity getUserByToken(String token);

	public void updateUserEmailVerificationStatus(UserEntity userEntity);
	
	public boolean isEmailExits(String email);
	
	public UserEntity updatePassword(String newPassword, UserEntity userEntity);

}
