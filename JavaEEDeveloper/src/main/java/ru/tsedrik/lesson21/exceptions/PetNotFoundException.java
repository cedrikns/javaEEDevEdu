package ru.tsedrik.lesson21.exceptions;

public class PetNotFoundException extends RuntimeException{
    public PetNotFoundException() {
    }

    public PetNotFoundException(String message) {
        super(message);
    }
}
