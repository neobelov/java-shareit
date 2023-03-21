package ru.practicum.shareit.booking.converter;

import org.springframework.core.convert.converter.Converter;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.exceptions.UnsupportedStateException;

import java.util.Arrays;
import java.util.Objects;

public class StringToBookingStateConverter implements Converter<String, BookingState> {
    @Override
    public BookingState convert(String source) {
        if (Arrays.stream(BookingState.values()).noneMatch(bookingState -> Objects.equals(bookingState.toString(), source))) {
            throw new UnsupportedStateException("Unknown state: " + source);
        }
        return BookingState.valueOf(source.toUpperCase());
    }
}