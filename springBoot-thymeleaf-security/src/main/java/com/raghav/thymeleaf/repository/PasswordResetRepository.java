package com.raghav.thymeleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raghav.thymeleaf.entity.PasswordResetTokenEntity;


@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetTokenEntity, Long> {

	public PasswordResetTokenEntity findByToken(String token);
}
