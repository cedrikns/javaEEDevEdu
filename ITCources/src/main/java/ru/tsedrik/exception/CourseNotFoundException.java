package ru.tsedrik.exception;

public class CourseNotFoundException extends RuntimeException{

    public CourseNotFoundException() {
    }

    public CourseNotFoundException(String message) {
        super(message);
    }
}