package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class BookControllerTest {

    private BookController bookController;
    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        libraryService = mock(LibraryService.class);
        bookController = new BookController(libraryService);
    }

    @Test
    void addBook_ShouldReturnSuccess() {
        Book book = new Book("123", "test", "test", true);

        ResponseEntity<String> response = bookController.addBook(book);

        verify(libraryService).addBook(book);
        assertEquals("Book added successfully", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
