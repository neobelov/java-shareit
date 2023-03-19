package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    void deleteWithOwnerCheck(Long id, Long owner);

    List<Item> getAllWithOwnerCheck(Long owner);

    List<Item> getAll();

    List<Item> searchItems(String text);

    Item add(Item item);

    Item update(Item item);

    Item replace(Item item);

    Item getById(Long id);

    void deleteById(Long id);

    Boolean exists(Long id);
}
