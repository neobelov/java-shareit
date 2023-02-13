package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.validation.PostInfo;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.groups.Default;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }
    @GetMapping("/{userId}")
    public User getById(@PathVariable int userId) {
        return userService.getById(userId);
    }
    @PostMapping
    public User post(@Validated({PostInfo.class, Default.class}) @RequestBody User user) {
        return userService.post(user);
    }

    @PatchMapping("/{userId}")
    public User patch(@Validated(Default.class) @RequestBody User user, @PathVariable Integer userId) {
        user.setId(userId);
        return userService.patch(user);
    }

    @DeleteMapping("/{userId}")
    public User delete(@PathVariable Integer userId) {
        return userService.delete(userId);
    }
}
