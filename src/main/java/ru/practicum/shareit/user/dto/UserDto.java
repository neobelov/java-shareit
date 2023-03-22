package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.exceptions.validation.PostInfo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotNull(groups = PostInfo.class, message = "User name can't be null")
    @NotEmpty(groups = PostInfo.class, message = "User name can't be empty")
    @NotBlank(groups = PostInfo.class, message = "User name can't be blank")
    private String name;

    @Email (message = "User email must be valid email")
    @NotNull(groups = PostInfo.class, message = "User email can't be null")
    @NotEmpty(groups = PostInfo.class, message = "User email can't be empty")
    @NotBlank(groups = PostInfo.class, message = "User email can't be blank")
    private String email;
}