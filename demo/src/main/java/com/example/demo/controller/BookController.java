package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        libraryService.addBook(book);
        return ResponseEntity.ok("Book added successfully");
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Book book = libraryService.getBookByIsbn(isbn);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) {
        boolean removed = libraryService.deleteBook(isbn);
        if (removed) {
            return ResponseEntity.ok("Book deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String author) {
        if (title != null) {
            return libraryService.searchBookByTitle(title);
        } else if (author != null) {
            return libraryService.searchBookByAuthor(author);
        } else {
            throw new IllegalArgumentException("Book that you search does not exist");
        }
    }
}
