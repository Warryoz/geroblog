package com.springboot.blog.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    //handle specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false) );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false) );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                        WebRequest webRequest) {
        // Extract the root cause of the exception
        Throwable rootCause = ex.getRootCause();

        // Check if the root cause is related to unique constraint violation
        // Extract the duplicate column value from the exception message
        // Create a concise error message
        String errorMessage = extractIntegrityViolationMessage(rootCause.getMessage());

        // Return the error message and the HTTP status code
        ErrorDetails errorDetails = new ErrorDetails(new Date(), errorMessage,
                webRequest.getDescription(false) );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Helper method to extract the duplicate column value from the exception message
    private static String extractIntegrityViolationMessage(String message) {
        // Implement the logic to extract the duplicate column value from the exception message
        // You might use regular expressions, String splitting, or any other suitable approach here.
        // For simplicity, let's assume the message format is "[...]: (column_name)=(value) [...]"
        int startIndex = message.indexOf("Detail:");
        int endIndex = message.indexOf(".");
        return message.substring(startIndex, endIndex);
    }

    // Validation
    //First way extending to the class ResponseEntityExceptionHandler
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err ->{
           String fieldName =  ((FieldError)err).getField();
           String message = err.getDefaultMessage();
           errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    //Create custom exception using the class MethodArgumentNotValidException
/*    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                               WebRequest webRequest){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(err ->{
            String fieldName =  ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }*/

    // global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false) );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
