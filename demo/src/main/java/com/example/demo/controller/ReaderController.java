package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.Reader;
import com.example.demo.observer.NotificationService;
import com.example.demo.observer.ReaderObserver;
import com.example.demo.service.LibraryService;
import com.example.demo.service.ReaderService;
import com.example.demo.util.Librarian;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReaderController {

    private final ReaderService readerService;
    private final NotificationService notificationService;
    private final LibraryService libraryService;

    public ReaderController(ReaderService readerService, NotificationService notificationService, LibraryService libraryService) {
        this.readerService = readerService;
        this.notificationService = notificationService;
        this.libraryService = libraryService;
    }

    @PostMapping
    public ResponseEntity<String> addReader(@RequestBody Reader reader) {


        if( reader.getEmail() == null || reader.getEmail().isEmpty() ) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        if(readerService.existsByEmail(reader.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        readerService.addReader(reader);

        ReaderObserver observer = new ReaderObserver(reader);
        notificationService.registerObserver(observer);

        return ResponseEntity.ok("Reader added and subscribed to notifications.");
    }

    @GetMapping
    public List<Reader> getAllReaders() {
        return readerService.getAllReaders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable String id) {
        Reader reader = readerService.getReaderById(id);
        if (reader != null) {
            return ResponseEntity.ok(reader);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/{isbn}")
    public ResponseEntity<String> rentReader(@PathVariable String id, @PathVariable String isbn) {
        Reader reader = readerService.getReaderById(id);
        Book book = libraryService.getBookByIsbn(isbn);

        if (reader == null || book == null) {
            return ResponseEntity.badRequest().body("Reader or Book not found");
        }

        if (reader.getRentedBook().containsKey(book.getIsbn())) {
            return ResponseEntity.badRequest().body("This book is already rented by this reader.");
        }

        if (reader.getRentedBook().size() >= 5) {
            return ResponseEntity.badRequest().body("Reader can't rent more than 5 books.");
        }

        if (!book.isAvailable()) {
            ReaderObserver observer = new ReaderObserver(reader);
            book.addToWaitlist(observer);
            Librarian.getInstance().logAction("Reader [" + reader.getId() + "] added to waitlist for book [" + book.getTitle() + "]");
            return ResponseEntity.ok("Book is not available. You've been added to the waitlist.");
        }

        reader.getRentedBook().put(book.getIsbn(), LocalDate.now());
        book.setCount(book.getCount() - 1);

        Librarian.getInstance().logAction("Reader [" + reader.getId() + "] rented book [" + book.getTitle() + "]");

        return ResponseEntity.ok("Book is rented successfully!");
    }


    @PostMapping("/return/{id}/{isbn}")
    public ResponseEntity<String> returnReader(@PathVariable String id, @PathVariable String isbn) {
        Reader reader = readerService.getReaderById(id);
        Book book = libraryService.getBookByIsbn(isbn);

        if (reader == null || book == null) {
            return ResponseEntity.badRequest().body("Reader or Book not found.");
        }

        if (!reader.getRentedBook().containsKey(isbn)) {
            return ResponseEntity.badRequest().body("This book is not rented by the reader.");
        }

        LocalDate rentedDate = reader.getRentedBook().get(isbn);
        LocalDate dueDate = rentedDate.plusDays(7);
        LocalDate today = LocalDate.now();

        reader.getRentedBook().remove(isbn);
        book.setCount(book.getCount() + 1);

        if (today.isAfter(dueDate)) {
            Librarian.getInstance().logAction("Reader [" + reader.getId() + "] returned overdue book [" + book.getTitle() + "]");
            return ResponseEntity.ok("Book returned, but it was overdue!");
        }

        Librarian.getInstance().logAction("Reader [" + reader.getId() + "] returned book [" + book.getTitle() + "]");

        if (!book.getWaitlist().isEmpty()) {
            ReaderObserver next = book.pollWaitlist();
            next.update("Book '" + book.getTitle() + "' is now available!");
        }

        return ResponseEntity.ok("Book returned successfully!");
    }
}
