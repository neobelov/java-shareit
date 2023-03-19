package ru.practicum.shareit.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionsConfig {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorResponse handleBadRequest(MethodArgumentNotValidException ex) {
        StringBuilder errorBuilder = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorBuilder.append(fieldError.getDefaultMessage());
            errorBuilder.append(" AND ");
        }
        String errorMessages = errorBuilder.substring(0, errorBuilder.length() - 5);
        log.warn(errorMessages);
        return new ErrorResponse("error", errorMessages);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingRequestHeaderException.class})
    public ErrorResponse handleMissingHeader(MissingRequestHeaderException ex) {
        log.warn(ex.getMessage());
        return new ErrorResponse("error", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({DuplicateUserEmailException.class})
    public ErrorResponse handleConflict(RuntimeException ex) {
        log.warn(ex.getMessage());
        return new ErrorResponse("error", ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleNotFound(RuntimeException ex) {
        log.warn(ex.getMessage());
        return new ErrorResponse("error", ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(NoRightsException.class)
    public ErrorResponse handleForbidden(RuntimeException ex) {
        log.warn(ex.getMessage());
        return new ErrorResponse("error", ex.getMessage());
    }



}
