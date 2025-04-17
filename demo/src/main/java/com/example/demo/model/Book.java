package com.example.demo.model;

import com.example.demo.observer.ReaderObserver;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;
import java.util.Queue;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean available;
    private int count;
    @JsonIgnore
    private Queue<ReaderObserver> waitlist = new LinkedList<>();

    public Book() {
        this.count = 5;
    }

    public Book(String isbn, String title, String author, boolean available) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.count= 5;
    }

    public Queue<ReaderObserver> getWaitlist() {
        return waitlist;
    }

    public void addToWaitlist(ReaderObserver observer) {
        waitlist.add(observer);
    }

    public ReaderObserver pollWaitlist() {
        return waitlist.poll();
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCount(){return count;}

    public void setCount(int count){this.count=count;}

    public boolean isAvailable() {
        return count> 0;
    }
}
