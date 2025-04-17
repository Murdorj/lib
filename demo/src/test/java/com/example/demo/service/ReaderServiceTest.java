package com.example.demo.service;

import com.example.demo.model.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderServiceTest {

    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderService();
    }

    @Test
    public void addReader() {
        Reader reader = new Reader("R001", "John", "john@example.com");
        readerService.addReader(reader);
        Reader found = readerService.getReaderById("R001");

        assertNotNull(found);
        assertEquals("John", found.getName());
    }

    @Test
    public void getReaderById() {
        Reader reader = new Reader("R001", "John", "john@example.com");

        readerService.addReader(reader);
        Reader found = readerService.getReaderById("R001");

        assertNotNull(found);
        assertEquals("John", found.getName());
    }

    @Test
    public void existsReader() {
        Reader reader = new Reader("R001", "John", "john@example.com");
        readerService.addReader(reader);

        boolean exists = readerService.existsByEmail("john@example.com");

        assertTrue(exists);
    }

    @Test
    public void nonExistentReaderShouldReturnFalse() {
        boolean exists = readerService.existsByEmail("notfound@example.com");

        assertFalse(exists);
    }

}
