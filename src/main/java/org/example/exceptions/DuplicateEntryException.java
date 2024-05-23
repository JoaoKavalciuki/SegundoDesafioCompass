package org.example.exceptions;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String msg) {
        super(msg);
    }
}
