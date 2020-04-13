package com.raghav.thymeleaf.service.admin;

import java.util.List;
import java.util.Optional;

import com.raghav.thymeleaf.entity.admin.BookEntity;
import com.raghav.thymeleaf.model.request.admin.AddBookRequestModel;
import com.raghav.thymeleaf.model.request.admin.UpdateBookRequestModel;

public interface BookService {
	
	public BookEntity saveBook(AddBookRequestModel addBookRequestModel);
	
	public List<BookEntity> getAllBooks();
	
	public Optional<BookEntity> bookDetail(Long id);

	public BookEntity findOne(Long id);
	
	public BookEntity updateBook(UpdateBookRequestModel updateBookRequestModel);

}
