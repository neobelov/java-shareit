package ru.practicum.shareit.exceptions;

public class CommentNoBookingException extends RuntimeException{
    public CommentNoBookingException(String message) {
        super(message);
    }
}
