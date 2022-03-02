package com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.interactor;

import java.util.List;

import com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.entity.Book;

public interface IBookService {
    List<Book> getBookList();
}
