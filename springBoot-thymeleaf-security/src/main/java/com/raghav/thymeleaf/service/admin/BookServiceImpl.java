package com.raghav.thymeleaf.service.admin;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raghav.thymeleaf.entity.admin.BookEntity;
import com.raghav.thymeleaf.model.request.admin.AddBookRequestModel;
import com.raghav.thymeleaf.model.request.admin.UpdateBookRequestModel;
import com.raghav.thymeleaf.repository.admin.BookRepository;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public BookEntity saveBook(AddBookRequestModel addBookRequestModel) {
		BookEntity bookEntity = new BookEntity();
		BeanUtils.copyProperties(addBookRequestModel, bookEntity);
		return bookRepository.saveAndFlush(bookEntity);
	}

	@Override
	public List<BookEntity> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<BookEntity> bookDetail(Long id) {
		return bookRepository.findById(id);
	}

	@Override
	public BookEntity findOne(Long id) {
		return bookRepository.getOne(id);
	}

	@Override
	public BookEntity updateBook(UpdateBookRequestModel updateBookRequestModel) {
		BookEntity bookEntity = new ModelMapper().map(updateBookRequestModel, BookEntity.class);
		return bookRepository.saveAndFlush(bookEntity);
	}

	
}
