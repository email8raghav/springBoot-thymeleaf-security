package com.raghav.thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raghav.thymeleaf.entity.UserEntity;



@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public UserEntity findByEmail(String email);
	
	public UserEntity findByEmailVerificationToken(String token);

	public boolean existsByEmail(String email);
	
}
