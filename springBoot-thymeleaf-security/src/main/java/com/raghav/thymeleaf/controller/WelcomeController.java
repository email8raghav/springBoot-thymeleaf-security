package com.raghav.thymeleaf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.raghav.thymeleaf.entity.UserEntity;
import com.raghav.thymeleaf.enums.Gender;
import com.raghav.thymeleaf.exception.InvalidTokenException;
import com.raghav.thymeleaf.model.request.PasswordRequestModel;
import com.raghav.thymeleaf.model.request.ResetPasswordRequestModel;
import com.raghav.thymeleaf.model.request.SignUpUserRequestModel;
import com.raghav.thymeleaf.model.response.SignUpUserResponseModel;
import com.raghav.thymeleaf.service.EmailService;
import com.raghav.thymeleaf.service.EmailVerificationService;
import com.raghav.thymeleaf.service.PasswordVerificationService;
import com.raghav.thymeleaf.service.UserService;

@Controller
public class WelcomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private EmailVerificationService emailVerificationService;

	@Autowired
	private PasswordVerificationService passwordVerificationService;

	private Logger log = LoggerFactory.getLogger(WelcomeController.class);

	@GetMapping("/")
	public String index(Model model) {

		Gender[] allGender = Gender.values();

		SignUpUserRequestModel signUpUserRequestModel = new SignUpUserRequestModel();
		model.addAttribute("signUpUserRequestModel", signUpUserRequestModel);
		model.addAttribute("allGender", allGender);

		return "index";
	}

	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}

	@GetMapping("/sign-up")
	public String getSignUpPage(Model model) {

		Gender[] allGender = Gender.values();

		SignUpUserRequestModel signUpUserRequestModel = new SignUpUserRequestModel();
		model.addAttribute("signUpUserRequestModel", signUpUserRequestModel);
		model.addAttribute("allGender", allGender);

		return "index";
	}

	@PostMapping("/sign-up")
	public String userSignUp(@Valid @ModelAttribute SignUpUserRequestModel signUpUserRequestModel, BindingResult result,
			HttpServletRequest request, Model model) {

		if (result.hasErrors()) {
			Gender[] allGender = Gender.values();
			model.addAttribute("allGender", allGender);
			return "index";
		}

		/*
		 * Verify that user not exits in database.
		 * 
		 * on behalf of email id provided.
		 */
		String email = signUpUserRequestModel.getEmail();
		UserEntity user = userService.getUserByEmail(email);
		// if (user != null)
		// throw new UserAlreadyExistsException("User email Id is already exists. Please
		// try other mail id. ");

		if (user != null) {
			String errMsg = "User email Id is already exists. Please try other mail id. ";
			model.addAttribute("errMsg", errMsg);

			Gender[] allGender = Gender.values();
			model.addAttribute("allGender", allGender);

			return "index";
		}

		// User not Exists Create a new User.

		/*
		 * Send user to the service layer
		 */
		UserEntity userEntity = userService.createNewUser(signUpUserRequestModel);

		/*
		 * Preparing email to send emailVerification Token
		 * 
		 */
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		String verificationUrl = appUrl + "/emailVerification?token=" + userEntity.getEmailVerificationToken();

		emailService.send(userEntity.getEmail(), verificationUrl);

		ModelMapper mapper = new ModelMapper();
		SignUpUserResponseModel newUser = mapper.map(userEntity, SignUpUserResponseModel.class);

		model.addAttribute("user", newUser);

		String newUserMeassage = "Please verify your email to login !!! check your email!!!";

		model.addAttribute("newUserMeassage", newUserMeassage);

		return "signup-info";

	}

	/*
	 * email verification url hits
	 * 
	 * http://localhost:8080/emailVerification?token=d8f8sd9f8sd7f8f6f6d7f6d76
	 * 
	 */
	@GetMapping("/emailVerification")
	public String emailVerification(@RequestParam String token, Model model) {

		Boolean status = emailVerificationService.verifyEmail(token);
		if (status) {
			// want to return successful something hehehe
			String message = "Email verified successfully !!! Please Login!!!";
			model.addAttribute("message", message);
			return "login";
		} else {
			throw new InvalidTokenException("Please Try Again !!! Token is expired!!!");
		}

	}

	/*
	 * forget-password-form to reset his/her password.
	 */
	@GetMapping("/forgetPassword")
	public String getforgetPasswordForm(Model model) {

		ResetPasswordRequestModel resetPasswordRequestModel = new ResetPasswordRequestModel();
		model.addAttribute("resetPasswordRequestModel", resetPasswordRequestModel);
		return "forget-password";
	}

	/*
	 * user post emailId to reset his/her password.
	 */
	@PostMapping("/forgetPassword")
	public String forgetPassword(@ModelAttribute ResetPasswordRequestModel resetPasswordRequestModel,
			HttpServletRequest request, Model model) {

		log.info("yes it is working properly" + resetPasswordRequestModel.getEmail());

		String passwordResetToken = passwordVerificationService
				.generatePsswordResetToken(resetPasswordRequestModel.getEmail());

		/*
		 * Preparing email to send passwordVerification Token
		 * 
		 */
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		String verificationUrl = appUrl + "/resetPassword?token=" + passwordResetToken;

		emailService.send(resetPasswordRequestModel.getEmail(), verificationUrl);

		String forgetPasswordEmailSent = "Email Sent to email address, Please Verify to reset your password!!!";

		model.addAttribute("forgetPasswordEmailSent", forgetPasswordEmailSent);

		return "forget-password";

	}

	/*
	 * email verification url hits
	 * 
	 * http://localhost:8080/resetPassword?token=d8f8sd9f8sd7f8f6f6d7f6d76
	 * 
	 */
	@GetMapping("/resetPassword")
	public String resetPassword(@RequestParam String token, Model model) {

		/*
		 * 
		 */
		System.out.println("************" + token);

		UserEntity userEntity = passwordVerificationService.verifyToken(token);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity, userEntity.getPassword(),
				userEntity.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		PasswordRequestModel passwordRequestModel = new PasswordRequestModel();
		model.addAttribute("passwordRequestModel", passwordRequestModel);

		return "reset-password";

	}

	@PostMapping("/resetPassword")
	public String changePassword(@ModelAttribute PasswordRequestModel passwordRequestModel, BindingResult result,
			Model model, @AuthenticationPrincipal UserEntity userEntity) {

		if (result.hasErrors()) {
			return "reset-password";
		}

		String newPassword = passwordRequestModel.getPassword();
		UserEntity user = userService.updatePassword(newPassword, userEntity);
		model.addAttribute("user", user);

		return "dash-board";

	}

	@GetMapping(path = "/dashboard")
	public String getDashboard(@AuthenticationPrincipal UserEntity user, ModelMap modelMap) {

		modelMap.put("user", user);

		return "dash-board";
	}

}
