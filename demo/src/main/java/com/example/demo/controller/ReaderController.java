package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.Reader;
import com.example.demo.observer.NotificationService;
import com.example.demo.observer.ReaderObserver;
import com.example.demo.service.LibraryService;
import com.example.demo.service.ReaderService;
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

    @PostMapping("/{id}/{rent}")
    public ResponseEntity<String> rentReader(@PathVariable String id, @PathVariable String rent) {
        Reader reader = readerService.getReaderById(id);
        Book book = libraryService.getBookByIsbn(rent);

        if (reader == null || book == null) {
            return ResponseEntity.badRequest().body("Reader or Book not found");
        }

        if (!book.isAvailable()) {
            return ResponseEntity.badRequest().body("Book rented successfully!");
        }

        if (reader.getRentedBook().size() > 5 ) {
            return ResponseEntity.badRequest().body("Book is already rented!");
        }
        reader.getRentedBook().put(book.getIsbn(), LocalDate.now());
        book.setAvailable(false);

        return ResponseEntity.ok("Book is rented!");
    }
}
