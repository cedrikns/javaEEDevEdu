package ru.tsedrik.exception;

public class MySQLException extends RuntimeException{
    public MySQLException() {
    }

    public MySQLException(String message) {
        super(message);
    }
}
