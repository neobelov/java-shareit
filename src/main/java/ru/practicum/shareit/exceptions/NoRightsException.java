package ru.practicum.shareit.exceptions;

public class NoRightsException extends RuntimeException {
    public NoRightsException(String message) {
        super(message);
    }
}
