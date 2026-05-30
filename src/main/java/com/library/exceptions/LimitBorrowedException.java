package com.library.exceptions;

public class LimitBorrowedException extends RuntimeException {
    public LimitBorrowedException(String message) {
        super(message);
    }
}