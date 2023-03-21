package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.model.Booking;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemWithBookings {
    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Long owner;

    private ItemBookingDto lastBooking;

    private ItemBookingDto nextBooking;
}
