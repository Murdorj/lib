package com.example.demo.observer;

import com.example.demo.model.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ReaderObserver implements Observer {

    private static final Logger logger = LoggerFactory.getLogger(ReaderObserver.class);
    private final Reader reader;

    public ReaderObserver(Reader reader) {
        this.reader = reader;
    }

    @Override
    public void update(String message) {
        logger.info("📩 Notifying {} ({}): {}", reader.getName(), reader.getEmail(), message);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ReaderObserver that = (ReaderObserver) obj;
        return reader.getId().equals(that.reader.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(reader.getId());
    }
}
