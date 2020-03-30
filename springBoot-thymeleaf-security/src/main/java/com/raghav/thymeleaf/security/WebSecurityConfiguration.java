package com.raghav.thymeleaf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.raghav.thymeleaf.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	// @Autowired
	// private Environment env;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private static final String[] PUBLIC_MATCHERS = { "/webjars/**", "/css/**", "/js/**", "/images/**", "/", "/newUser",
			"/emailVerification", "/sign-up", "/resetPassword", "/forgetPassword", "/profile", "/h2-console/**",
			"/login", "/fonts/**", "/bookshelf", "/bookDetail/**", "/hours", "/faq", "/searchByCategory",
			"/searchBook", "/test" };
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// for h2-console
		http.headers().frameOptions().disable();

		http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();

		http.csrf().disable().cors().disable()
		.formLogin()
		.failureUrl("/login?error")
		.defaultSuccessUrl("/dashboard", true)
		.loginPage("/login")
		.permitAll()
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/?logout")
		.deleteCookies("remember-me")
		.permitAll()
		.and()
		.rememberMe();
	}

}
