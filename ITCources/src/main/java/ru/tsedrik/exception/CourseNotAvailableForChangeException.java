package ru.tsedrik.exception;

public class CourseNotAvailableForChangeException extends RuntimeException{

    public CourseNotAvailableForChangeException() {
    }

    public CourseNotAvailableForChangeException(String message) {
        super(message);
    }
}
