package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.storage.InMemoryStorage;
import ru.practicum.shareit.user.model.User;

@Component
public class InMemoryUserStorage extends InMemoryStorage<User> implements UserStorage {
}
