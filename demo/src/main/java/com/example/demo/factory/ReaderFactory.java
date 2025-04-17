package com.example.demo.factory;

import com.example.demo.model.Reader;

public class ReaderFactory {

    public static Reader createReader(String id, String email, String name) {
        return new Reader(id, email, name);
    }
}