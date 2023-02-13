package ru.practicum.shareit.user.service;

import ru.practicum.shareit.storage.Storage;
import ru.practicum.shareit.user.model.User;


public interface UserService extends Storage<User> {
    User patch(User obj);
}
