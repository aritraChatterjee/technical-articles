package com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.repository;

import java.util.List;

import com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.entity.Book;

public interface BookRepository {
    List<Book> getBooks();
}
