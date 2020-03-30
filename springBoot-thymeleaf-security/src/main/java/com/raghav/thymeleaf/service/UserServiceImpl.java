package com.raghav.thymeleaf.service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.raghav.thymeleaf.entity.AuthorityEntity;
import com.raghav.thymeleaf.entity.UserEntity;
import com.raghav.thymeleaf.model.request.SignUpUserRequestModel;
import com.raghav.thymeleaf.repository.UserRepository;
import com.raghav.thymeleaf.utility.DateParser;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@Autowired
	private EmailVerificationService emailVerificationService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
		return userEntity;
	}

	@Override
	public UserEntity createNewUser(SignUpUserRequestModel signUpUserRequestModel) {
		 
		ModelMapper modelmapper  = new ModelMapper();
		UserEntity userEntity = modelmapper.map(signUpUserRequestModel, UserEntity.class);
		
		userEntity.setPublicUserId(UUID.randomUUID().toString());
		
		userEntity.setEncryptedPassword(bcryptPasswordEncoder.encode(signUpUserRequestModel.getPassword()));
		
		userEntity.setDateOfBirth(DateParser.localDateParser(signUpUserRequestModel.getDob()));
		
		userEntity.setEmailIsVerified(false);
		
		String token = emailVerificationService.generateEmailVerificationToken(userEntity.getPublicUserId());
		
		userEntity.setEmailVerificationToken(token);
		
		AuthorityEntity userAuthority = new AuthorityEntity();
		userAuthority.setAuthority("ROLE_USER");
		userAuthority.setUserEntity(userEntity);
		
		Set<AuthorityEntity> authorities = new HashSet<>();
		authorities.add(userAuthority);
		
		userEntity.setAuthorities(authorities);
		
		return userRepository.save(userEntity);
	}

	@Override
	public UserEntity getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public UserEntity getUserByToken(String token) {
		return userRepository.findByEmailVerificationToken(token);
	}

	@Override
	public void updateUserEmailVerificationStatus(UserEntity userEntity) {
		userRepository.save(userEntity);
		
	}

	@Override
	public boolean isEmailExits(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public UserEntity updatePassword(String newPassword, UserEntity userEntity) {
		userEntity.setEncryptedPassword(bcryptPasswordEncoder.encode(newPassword));
		return userRepository.save(userEntity);
	}

	
}
