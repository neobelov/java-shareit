package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.utilities.Constants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public Booking post(@RequestHeader(Constants.SHARER_USER_ID) @NotNull Long bookerId,
                        @RequestBody @Validated BookingDto dto) {
        return bookingService.add(dto, bookerId);
    }

    @PatchMapping("/{bookingId}")
    public Booking approveBooking(@RequestHeader(Constants.SHARER_USER_ID) @NotNull Long ownerId,
                                   @PathVariable("bookingId") @NotNull Long bookingId,
                                   @RequestParam("approved") @NotNull Boolean isApproved) {
        return bookingService.approveBooking(ownerId,bookingId,isApproved);
    }

    @GetMapping("/{bookingId}")
    public Booking get(@RequestHeader(Constants.SHARER_USER_ID) Long userId,
                       @PathVariable("bookingId") Long bookingId) {
        return bookingService.get(userId, bookingId);
    }

    @GetMapping
    public List<Booking> getBookings(@RequestHeader(Constants.SHARER_USER_ID) Long bookerId,
                                                           @RequestParam(value = "state", defaultValue = "ALL") BookingState state) {
        return bookingService.getBookings(bookerId, state);
    }

    @GetMapping("/owner")
    public List<Booking> getOwnerBookings(@RequestHeader(Constants.SHARER_USER_ID) @NotNull Long ownerId,
                                                         @RequestParam(value = "state", defaultValue = "ALL") @Valid BookingState state) {
        return bookingService.getOwnerBookings(ownerId, state);
    }
}