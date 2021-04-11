package com.example.five9demo.Exceptions;

import com.example.five9demo.responses.OrganizationResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * A generic exception handler mapping specific exceptions to adequate error responses
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String INVALID_REQUEST = "Invalid request";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return handleBadRequest(new RuntimeException(ex.getMessage()), request);
    }

    @ExceptionHandler(value = {
        ConstraintViolationException.class, IllegalArgumentException.class,
        IllegalStateException.class
    })
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        OrganizationResponse response = new OrganizationResponse();
        response.setInformation(INVALID_REQUEST);
        response.setMessage(ex.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        response.setMessageCode(String.valueOf(httpStatus.value()));
        return handleExceptionInternal(ex, response, new HttpHeaders(), httpStatus, request);
    }
}
