package ru.tsedrik.lesson17.hometask1.exceptions;

public class NoDefinedSortStrategyException extends RuntimeException{
    public NoDefinedSortStrategyException() {
    }

    public NoDefinedSortStrategyException(String message) {
        super(message);
    }
}
