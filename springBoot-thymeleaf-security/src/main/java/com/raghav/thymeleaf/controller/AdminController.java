package com.raghav.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.raghav.thymeleaf.model.request.AddBookRequestModel;

@Controller
public class AdminController {
	
	
	@GetMapping("/addBook")
	public String addBook(Model model) {
		AddBookRequestModel addBookRequstModel = new AddBookRequestModel();
		model.addAttribute("book", addBookRequstModel);
		return "admin/add-book";
	}

	
	
}
