package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.storage.Storage;

import java.util.List;

public interface ItemService extends Storage<Item> {
    Item patch(Item obj);

    Item deleteWithOwnerCheck(Integer id, Integer owner);

    List<Item> getAllWithOwnerCheck(Integer owner);

    List<Item> searchItems(String text);

}
