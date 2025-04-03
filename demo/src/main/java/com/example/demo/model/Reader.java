package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reader {
    private String id;
    private String name;
    private String email;

    private Map<String, LocalDate> rentedBook = new HashMap<>();

    public Reader() {}

    public Reader(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() { return id; }git
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Map<String, LocalDate> getRentedBook() {
        return rentedBook;
    }

    public void setRentedBook(Map<String, LocalDate> rentedBook) {
        this.rentedBook = rentedBook;
    }
}
