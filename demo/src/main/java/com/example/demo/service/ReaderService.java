package com.example.demo.service;

import com.example.demo.model.Reader;
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
}
