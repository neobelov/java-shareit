package ru.practicum.shareit.exceptions;

public class ChangeApprovedBookingStatusException extends RuntimeException {
    public ChangeApprovedBookingStatusException(String message) {
        super(message);
    }
}
