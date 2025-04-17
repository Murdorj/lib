package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.observer.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryServiceTest {

    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        libraryService = new LibraryService(new NotificationService());
    }

    @Test
    void addBook_shouldStoreBook() {
        Book book = new Book("123", "test_title", "test_author", true);

        libraryService.addBook(book);
        Book found = libraryService.getBookByIsbn("123");

        assertNotNull(found);
        assertEquals("test_title", found.getTitle());
    }

    @Test
    void getAllBooks_shouldReturnAllBooks() {
        libraryService.addBook(new Book("123", "test_title1", "test_author1", true));
        libraryService.addBook(new Book("456", "test_title2", "test_author2", true));

        List<Book> books = libraryService.getAllBooks();

        assertEquals(2, books.size());
    }

    @Test
    void deleteBook_shouldDeleteBook() {
        libraryService.addBook(new Book("123", "test_title1", "test_author1", true));
        boolean result = libraryService.deleteBook("123");

        assertTrue(result);
        assertNull(libraryService.getBookByIsbn("123"));
    }

    @Test
    void findBookByTitle_shouldReturnBooks() {
        libraryService.addBook(new Book("123", "test_title1", "test_author1", true));
        libraryService.addBook(new Book("456", "test_title2", "test_author2", true));

        List<Book> result = libraryService.searchBookByTitle("test");

        assertEquals(2, result.size());
    }

    @Test
    void findBookByAuthor_shouldReturnBooks() {
        libraryService.addBook(new Book("123", "test_title1", "test_author1", true));
        libraryService.addBook(new Book("456", "test_title2", "test_author2", true));

        List<Book> result = libraryService.searchBookByAuthor("test");

        assertEquals(2, result.size());
    }
}
