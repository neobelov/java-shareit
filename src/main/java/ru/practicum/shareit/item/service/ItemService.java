package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemWithBookings;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    void delete(Long id, Long owner);

    List<ItemWithBookings> getAll(Long owner);

    List<Item> searchItems(String text);

    Item add(Item item);

    Item update(Item item);

    Item replace(Item item);

    Item getById(Long id);

    ItemWithBookings getWithBookingsById(Long id, Long ownerId);

    CommentDto addComment(Long itemId, Long bookerId, CommentDto dto);
}
