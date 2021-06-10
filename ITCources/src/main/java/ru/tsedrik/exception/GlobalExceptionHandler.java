package ru.tsedrik.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Класс для перехвата исключений
 */
@ControllerAdvice(basePackages = "ru.tsedrik.controller")
public class GlobalExceptionHandler {

    /**
     * Метод для перехвата и обработки исключения CourseNotFoundException
     */
    @ExceptionHandler({CourseNotFoundException.class, StudentNotFoundException.class})
    public ResponseEntity<ResponseError> entityNotFoundException(RuntimeException e){
        ResponseError responseError = new ResponseError(System.currentTimeMillis(),
                e.getMessage(),
                "entityNotFoundException");

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод для перехвата и обработки исключения CourseNotAvailableForChangeException
     */
    @ExceptionHandler(CourseNotAvailableForChangeException.class)
    public ResponseEntity<ResponseError> courseNotAvailableForChangeException(CourseNotAvailableForChangeException e){
        ResponseError responseError = new ResponseError(System.currentTimeMillis(),
                e.getMessage(),
                "courseNotAvailableForChangeException");

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод для перехвата и обработки исключения IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> illegalArgumentException(IllegalArgumentException e){
        ResponseError responseError = new ResponseError(System.currentTimeMillis(),
                e.getMessage(),
                "illegalArgumentException");

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Метод для перехвата и обработки всех исключений
     * для которых нет отдельных методов
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> exception(Exception e){
        ResponseError responseError = new ResponseError(System.currentTimeMillis(),
                e.getMessage(),
                "undefinedException");

        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
