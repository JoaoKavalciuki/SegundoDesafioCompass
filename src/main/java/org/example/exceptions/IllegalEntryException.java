package org.example.exceptions;

public class IllegalEntryException extends RuntimeException {
    public IllegalEntryException(String msg) {
        super(msg);
    }
}
