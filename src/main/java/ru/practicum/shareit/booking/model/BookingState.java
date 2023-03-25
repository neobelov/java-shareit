package ru.practicum.shareit.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BookingState {
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED,
    ALL
}
