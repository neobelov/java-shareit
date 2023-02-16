package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.storage.InMemoryStorage;

@Component
public class InMemoryItemStorage extends InMemoryStorage<Item> implements ItemStorage {
}
