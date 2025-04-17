package com.example.demo.service;

import com.example.demo.factory.ReaderFactory;
import com.example.demo.model.Reader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReaderService {
    private final Map<String, Reader> readers = new HashMap<>();

    public void addReader(Reader reader) {
        readers.put(reader.getId(), reader);
    }

    public List<Reader> getAllReaders() {
        return new ArrayList<>(readers.values());
    }

    public Reader getReaderById (String id) {
        return readers.get(id);
    }

    public boolean existsByEmail (String email) {
        return readers.values().stream()
                .anyMatch(reader -> reader.getEmail().equalsIgnoreCase(email));

    }

    @PostConstruct
    public void init() {
        Reader reader = ReaderFactory.createReader("r001", "bat@gnail.com", "Bat-Erdene");
        addReader(reader);
        System.out.println(">> Анхны уншигч автоматаар үүсгэгдлээ: " + reader.getName());
    }
}
