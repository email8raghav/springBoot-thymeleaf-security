package com.raghav.thymeleaf;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.raghav.thymeleaf.entity.AuthorityEntity;
import com.raghav.thymeleaf.entity.UserEntity;
import com.raghav.thymeleaf.repository.UserRepository;
import com.raghav.thymeleaf.utility.DateParser;

@Component
public class InitialUsersSetup {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {

		System.out.println("From Application ready event...");

		UserEntity adminUser = new UserEntity();
		adminUser.setFirstName("admin");
		adminUser.setLastName("admin");
		adminUser.setEmail("email8raghav@gmail.com");
		adminUser.setDateOfBirth(DateParser.localDateParser("1987-03-22"));
		adminUser.setEmailIsVerified(true);
		adminUser.setPublicUserId(UUID.randomUUID().toString());
		adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
		
		AuthorityEntity adminAuthority = new AuthorityEntity();
		adminAuthority.setAuthority("ROLE_ADMIN");
		adminAuthority.setUserEntity(adminUser);
		
		Set<AuthorityEntity> adminAuthorities = new HashSet<>();
		adminAuthorities.add(adminAuthority);
		
		adminUser.setAuthorities(adminAuthorities);

		userRepository.save(adminUser);
		
		UserEntity dummyUser = new UserEntity();
		dummyUser.setFirstName("DummyUser");
		dummyUser.setLastName("yummy");
		dummyUser.setEmail("softraghavendra@gmail.com");
		dummyUser.setDateOfBirth(DateParser.localDateParser("1987-03-22"));
		dummyUser.setEmailIsVerified(true);
		dummyUser.setPublicUserId(UUID.randomUUID().toString());
		dummyUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
		
		AuthorityEntity userAuthority = new AuthorityEntity();
		userAuthority.setAuthority("ROLE_USER");
		userAuthority.setUserEntity(dummyUser);
		
		Set<AuthorityEntity> authorities = new HashSet<>();
		authorities.add(userAuthority);
		
		dummyUser.setAuthorities(authorities);
		
		userRepository.save(dummyUser);

	}

}
