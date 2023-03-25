package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ResourceNotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User replace(User user) {
        if (!existsById(user.getId())) {
            throw new ResourceNotFoundException("user with id " + user.getId() + " is not found");
        }
        return userRepository.save(user);
    }

    @Override
    public Boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("user with id " + id + " is not found");
        }
        return userOptional.get();
    }

    @Override
    public User update(User user) {
        User userForUpdate = getById(user.getId());
        if (user.getEmail() != null) {
            userForUpdate.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            userForUpdate.setName(user.getName());
        }
        return userRepository.save(userForUpdate);
    }

    @Override
    public void deleteById(Long id) {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("user with id " + id + " is not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
