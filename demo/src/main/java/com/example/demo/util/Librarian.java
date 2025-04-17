package com.example.demo.util;

public class Librarian {

    private static Librarian instance;

    private Librarian() {
        System.out.println("📚 Librarian instance үүссэн.");
    }

    public static Librarian getInstance() {
        if (instance == null) {
            instance = new Librarian();
        }
        return instance;
    }

    public void logAction(String action) {
        System.out.println("[Librarian log] " + action);
    }
}
