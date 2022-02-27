package com.codesandwires.tutorial.pattern.architecture.hexagonal.userside.userinterface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.entity.Book;
import com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.interactor.BookService;
import com.codesandwires.tutorial.pattern.architecture.hexagonal.serverside.datasource.FileDataSource;
import com.codesandwires.tutorial.pattern.architecture.hexagonal.serverside.datasource.MongoDBDataSource;
import com.codesandwires.tutorial.pattern.architecture.hexagonal.TestHelper;

class ConsoleUIUnitTest {

    @Test
    void givenFileDatasource_whenConsoleUIRequestsBooks_thenBooksReceivedSuccessfully() {
        FileDataSource fileDataSource = Mockito.spy(FileDataSource.class);
        BookService bookService = new BookService(fileDataSource);
        BookConsoleUI userInterface = new BookConsoleUI(bookService);

        // stubbing the response from the file
        when(fileDataSource.getBooksFromFile()).thenReturn(TestHelper.mockDataFromFile());

        BookConsoleUI spyUserInterface = Mockito.spy(userInterface);
        spyUserInterface.showBooks();

        ArgumentCaptor<List> listArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(spyUserInterface, times(1)).printBooksToConsole(listArgumentCaptor.capture());

        List<Book> actualBooks = (List<Book>) ((List<?>) listArgumentCaptor.getValue());
        assertThat(actualBooks.size()).isEqualTo(2);

        assertCorrectListOfBooks(actualBooks);

    }

    @Test
    void givenMongoDBDatasource_whenConsoleUIRequestsBooks_thenBooksReceivedSuccessfully() {
        MongoDBDataSource mongoDBDataSource = Mockito.spy(MongoDBDataSource.class);
        BookService bookService = new BookService(mongoDBDataSource);
        BookConsoleUI userInterface = new BookConsoleUI(bookService);

        // stubbing the response from MongoDB
        when(mongoDBDataSource.getAllBookDocuments()).thenReturn(TestHelper.mockDataFromMongoDb());

        BookConsoleUI spyUserInterface = Mockito.spy(userInterface);
        spyUserInterface.showBooks();

        ArgumentCaptor<List> listArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(spyUserInterface, times(1)).printBooksToConsole(listArgumentCaptor.capture());

        List<Book> actualBooks = (List<Book>) ((List<?>) listArgumentCaptor.getValue());
        assertThat(actualBooks.size()).isEqualTo(2);

        assertCorrectListOfBooks(actualBooks);
    }

    private void assertCorrectListOfBooks(List<Book> books) {
        for (Book book : books) {
            if (book.getId() == 1) {
                assertThat(book.getName()).isEqualTo("The Jungle Book");
                assertThat(book.getAuthor()).isEqualTo("Rudyard Kipling");
            }
            if (book.getId() == 2) {
                assertThat(book.getName()).isEqualTo("Kane and Abel");
                assertThat(book.getAuthor()).isEqualTo("Jeffrey Archer");
            }
        }
    }

}