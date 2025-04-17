package com.example.demo.factory;


import com.example.demo.model.Book;

public class BookFactory {

    public static Book createBook(String isbn, String title, String author) {
        return new Book(isbn, title, author, true);
    }
}
