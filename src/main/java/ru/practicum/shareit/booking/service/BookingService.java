package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.util.List;

public interface BookingService {
    Booking add(BookingDto dto, Long bookerId);

    Booking approveBooking(Long ownerId, Long bookingId, Boolean isApproved);

    Booking get(Long userId, Long bookingId);

    List<Booking> getBookings(Long bookerId, BookingState state);

    List<Booking> getOwnerBookings(Long ownerId, BookingState state);
}
