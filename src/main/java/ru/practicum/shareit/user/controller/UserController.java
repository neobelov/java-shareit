package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.validation.PostInfo;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper = new UserMapper();

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll().parallelStream().map(userMapper::mapToUserDto).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserDto getById(@PathVariable Long userId) {
        return userMapper.mapToUserDto(userService.getById(userId));
    }

    @PostMapping
    public UserDto post(@Validated({PostInfo.class, Default.class}) @RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        User addedUser = userService.add(user);
        return userMapper.mapToUserDto(addedUser);
    }

    @PatchMapping("/{userId}")
    public UserDto patch(@Validated(Default.class) @RequestBody UserDto userDto, @PathVariable Long userId) {
        userDto.setId(userId);
        User user = userMapper.mapToUser(userDto);
        User updatedUser = userService.update(user);
        return userMapper.mapToUserDto(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        userService.deleteById(userId);
    }
}
