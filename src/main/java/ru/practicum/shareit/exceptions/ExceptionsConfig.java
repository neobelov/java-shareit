package ru.practicum.shareit.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exceptions.validation.ModelValidationException;

@RestControllerAdvice
@Slf4j
public class ExceptionsConfig {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        throw new ModelValidationException(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ModelValidationException.class})
    public ErrorResponse handleBadRequest(RuntimeException ex) {
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
}
