package com.raghav.thymeleaf.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.raghav.thymeleaf.enums.Gender;



@Entity
@Table(name = "user")
public class UserEntity implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1148668750413235570L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	@Column(nullable = false)
	private String publicUserId;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String encryptedPassword;
	

	@Column(columnDefinition = "DATE")
	private LocalDate dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private boolean emailIsVerified;
	
	private String emailVerificationToken;
	
	
	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<AuthorityEntity> authorities = new HashSet<>();
	
	public UserEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPublicUserId() {
		return publicUserId;
	}

	public void setPublicUserId(String publicUserId) {
		this.publicUserId = publicUserId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public boolean getEmailIsVerified() {
		return emailIsVerified;
	}

	public void setEmailIsVerified(boolean emailIsVerified) {
		this.emailIsVerified = emailIsVerified;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<AuthorityEntity> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return encryptedPassword;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return getEmailIsVerified();
	}
	
	

	
}
