package com.example.demo.observer;

import com.example.demo.model.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
