package com.raghav.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/my-account")
	public String getMyAccountPage() {
		return "my-account";
	}

	@GetMapping("/test")
	public String test(Model model) {

		String newUserMeassage = "Please verify your email to login !!! check your email!!!";

		model.addAttribute("newUserMeassage", newUserMeassage);

		model.addAttribute("message", "display this message");

		return "test";
	}

}
