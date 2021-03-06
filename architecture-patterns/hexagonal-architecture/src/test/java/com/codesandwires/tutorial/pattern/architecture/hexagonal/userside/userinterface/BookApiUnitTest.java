package com.codesandwires.tutorial.pattern.architecture.hexagonal.userside.userinterface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.codesandwires.tutorial.pattern.architecture.hexagonal.domain.interactor.BookService;
import com.codesandwires.tutorial.pattern.architecture.hexagonal.serverside.datasource.FileDataSource;
import com.codesandwires.tutorial.pattern.architecture.hexagonal.serverside.datasource.MongoDBDataSource;
import com.codesandwires.tutorial.pattern.architecture.hexagonal.TestHelper;

class BookApiUnitTest {

    private final String expectedJsonResponse = "[{\"id\":1,\"name\":\"The Jungle Book\",\"author\":\"Rudyard Kipling\"},{\"id\":2,\"name\":\"Kane and Abel\",\"author\":\"Jeffrey Archer\"}]";

    @Test
    void givenFileDatasource_whenBookAPIRequestsBooks_thenBooksReceivedSuccessfully() throws IOException {
        FileDataSource fileDataSource = Mockito.spy(FileDataSource.class);

        BookService bookService = new BookService(fileDataSource);

        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        BookApi bookApi = new BookApi(bookService);

        // stubbing the response from the file
        when(fileDataSource.getBooksFromFile()).thenReturn(TestHelper.mockDataFromFile());

        // stubbing servlet behavior
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        bookApi.doGet(httpServletRequest, httpServletResponse);

        String jsonResponseFromApi = stringWriter.getBuffer()
            .toString()
            .trim();

        assertThat(jsonResponseFromApi).isEqualTo(expectedJsonResponse);

    }

    @Test
    void givenMongoDBDatasource_whenBookAPIRequestsBooks_thenBooksReceivedSuccessfully() throws IOException {
        MongoDBDataSource mongoDBDataSource = Mockito.spy(MongoDBDataSource.class);

        BookService bookService = new BookService(mongoDBDataSource);

        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        BookApi bookApi = new BookApi(bookService);

        // stubbing the response from the file
        when(mongoDBDataSource.getAllBookDocuments()).thenReturn(TestHelper.mockDataFromMongoDb());

        // stubbing servlet behavior
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        bookApi.doGet(httpServletRequest, httpServletResponse);

        String jsonResponseFromApi = stringWriter.getBuffer()
            .toString()
            .trim();

        assertThat(jsonResponseFromApi).isEqualTo(expectedJsonResponse);

    }
}
