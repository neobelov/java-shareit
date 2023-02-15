package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.DuplicateUserEmailException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    Map<String, User> usersByEmail = new HashMap<>();

    @Override
    public User post(User obj) {
        if (usersByEmail.putIfAbsent(obj.getEmail(), obj) != null) {
            throw new DuplicateUserEmailException("User with email " + obj.getEmail() + " already exists");
        }
        return userStorage.post(obj);
    }

    @Override
    public User put(User obj) {
        return userStorage.put(obj);
    }

    @Override
    public User patch(User obj) {
        User user = userStorage.getById(obj.getId());
        if (obj.getEmail() != null) {
            if (usersByEmail.containsKey(obj.getEmail()) && !Objects.equals(usersByEmail.get(obj.getEmail()).getId(), obj.getId())) {
                throw new DuplicateUserEmailException("User with email " + obj.getEmail() + " already exists");
            }
            usersByEmail.remove(user.getEmail());
            user.setEmail(obj.getEmail());
        }
        if (obj.getName() != null) {
            user.setName(obj.getName());
        }
        userStorage.put(user);
        usersByEmail.put(user.getEmail(), user);
        return user;
    }

    @Override
    public User delete(Integer id) {
        User user = userStorage.delete(id);
        usersByEmail.remove(user.getEmail());
        return user;
    }

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }

    @Override
    public User getById(Integer id) {
        return userStorage.getById(id);
    }
}
