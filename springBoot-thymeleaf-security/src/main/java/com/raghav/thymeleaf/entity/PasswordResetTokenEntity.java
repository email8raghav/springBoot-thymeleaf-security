package com.raghav.thymeleaf.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "passwordResetToken")
public class PasswordResetTokenEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8936447051551464786L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String token; 
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;
	
	public PasswordResetTokenEntity() {
	}

	public PasswordResetTokenEntity(String token){
		this.token = token;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	
}
