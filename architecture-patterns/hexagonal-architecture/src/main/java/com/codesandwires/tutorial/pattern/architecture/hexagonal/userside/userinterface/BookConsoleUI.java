package com.codesandwires.tutorial.pattern.architecture.hexagonal.userside.userinterface;

import java.util.List;

import com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.entity.Book;
import com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.interactor.IBookService;

public class BookConsoleUI {
    private IBookService bookService;

    public BookConsoleUI(IBookService bookService) {
        this.bookService = bookService;
    }

    public void showBooks() {
        List<Book> books = bookService.getBookList();
        printBooksToConsole(books);
    }

    public void printBooksToConsole(List<Book> books) {
        // logic to print books to console goes here
    }
}
