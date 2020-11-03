package ru.tsedrik.lesson17.hometask1.exceptions;

public class NoDefinedSortStrategy extends RuntimeException{
    public NoDefinedSortStrategy() {
    }

    public NoDefinedSortStrategy(String message) {
        super(message);
    }
}
