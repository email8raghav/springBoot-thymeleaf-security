package com.raghav.thymeleaf.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.raghav.thymeleaf.entity.UserEntity;
import com.raghav.thymeleaf.entity.admin.BookEntity;
import com.raghav.thymeleaf.service.UserService;
import com.raghav.thymeleaf.service.admin.BookService;

@Controller
public class UserController {

	@Autowired
	private BookService bookService;

	@Autowired
	private UserService userService;

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

	@GetMapping("/bookShelf")
	public String bookshelf(Model model, @AuthenticationPrincipal UserEntity user) {
		model.addAttribute("user", user);

		List<BookEntity> bookList = bookService.getAllBooks();
		model.addAttribute("bookList", bookList);
		model.addAttribute("activeAll", true);

		return "book-shelf";
	}

	@GetMapping("/bookDetail")
	public String bookDetail(@PathParam("id") Long id, Model model, @AuthenticationPrincipal UserEntity user) {

		model.addAttribute("user", user);
		Optional<BookEntity> bookDetail = bookService.bookDetail(id);
		BookEntity book = null;
		if (bookDetail.isPresent()) {
			book = bookDetail.get();
		}
		model.addAttribute("book", book);

		List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		model.addAttribute("qtyList", qtyList);
		model.addAttribute("qty", 1);

		return "book-detail";
	}

}
