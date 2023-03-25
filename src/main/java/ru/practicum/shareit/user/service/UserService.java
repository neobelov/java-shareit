package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;

import java.util.List;


public interface UserService {
    User add(User user);

    User replace(User user);

    Boolean existsById(Long userId);

    User getById(Long id);

    User update(User user);

    void deleteById(Long id);

    List<User> getAll();
}
