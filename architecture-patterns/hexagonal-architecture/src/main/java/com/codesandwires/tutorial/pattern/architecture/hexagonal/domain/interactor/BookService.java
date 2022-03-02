package com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.interactor;

import java.util.List;

import com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.entity.Book;
import com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.repository.BookRepository;

public class BookService implements IBookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBookList() {
        return bookRepository.getAllBooks();
    }
}
