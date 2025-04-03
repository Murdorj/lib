package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.model.Reader;
import com.example.demo.observer.NotificationService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    private final Map<String, Book> bookMap = new HashMap<>();

    private final NotificationService notificationService;

    public LibraryService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void addBook(Book book) {
        bookMap.put(book.getIsbn(), book);

        if (book.isAvailable()) {
            notificationService.notifyObservers("Book [" + book.getTitle() + "] is now available!");
        }
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookMap.values());
    }

    public Book getBookByIsbn(String isbn) {
        return bookMap.get(isbn);
    }

    public boolean deleteBook(String isbn) {
        return bookMap.remove(isbn) != null;
    }

    public List <Book> searchBookByTitle(String title) {
        return bookMap.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List <Book> searchBookByAuthor(String author) {
        return bookMap.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }
}
