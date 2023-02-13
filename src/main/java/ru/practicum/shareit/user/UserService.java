package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.storage.InMemoryStorage;
import ru.practicum.shareit.storage.Storage;

import java.util.HashSet;
import java.util.Set;


public interface UserService extends Storage<User> {
    User patch(User obj);
}
