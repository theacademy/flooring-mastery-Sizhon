package com.mcp.FlooringMastery.Exceptions;

public class OrderDataPersistenceException extends Exception {
    public OrderDataPersistenceException(String message) {
        super(message);
    }

    public OrderDataPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
