package com.raghav.thymeleaf.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raghav.thymeleaf.entity.admin.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

}
