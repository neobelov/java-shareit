package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public ItemDto mapToItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable());
    }
    public Item mapToItem(ItemDto itemDto, Long owner) {
        return new Item(itemDto.getId(), itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable(), owner);
    }
    public ItemWithBookings mapToItemWithBookings(Item item, Booking lastBooking, Booking nextBooking) {
        return new ItemWithBookings(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner(),
                mapToItemBookingDto(lastBooking),
                mapToItemBookingDto(nextBooking));
    }

    public ItemBookingDto mapToItemBookingDto(Booking booking) {
        if (booking == null || booking.getBooker() == null) {
            return null;
        }
        return new ItemBookingDto(booking.getId(), booking.getBooker().getId());
    }
}
