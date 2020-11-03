package ru.tsedrik.lesson16.hometask1.exceptions;

public class PetNotFoundException extends RuntimeException{
    public PetNotFoundException() {
    }

    public PetNotFoundException(String message) {
        super(message);
    }
}
