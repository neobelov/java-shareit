package ru.practicum.shareit.user.model;

import lombok.*;
import ru.practicum.shareit.storage.StorageObject;
import ru.practicum.shareit.exceptions.validation.PostInfo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class User implements StorageObject<User> {
    private Integer id;

    @NotNull(groups = PostInfo.class, message = "User name can't be null")
    @NotEmpty(groups = PostInfo.class, message = "User name can't be empty")
    @NotBlank(groups = PostInfo.class, message = "User name can't be blank")
    private String name;

    @Email (message = "User email must be valid email")
    @NotNull(groups = PostInfo.class, message = "User email can't be null")
    @NotEmpty(groups = PostInfo.class, message = "User email can't be empty")
    @NotBlank(groups = PostInfo.class, message = "User email can't be blank")
    private String email;

    public User patch(User obj) {
        if (obj.getId() != null) {
            this.id = obj.getId();
        }
        if (obj.getName() != null) {
            this.name = obj.getName();
        }
        if (obj.getEmail() != null) {
            this.email = obj.getEmail();
        }
        return this;
    }
}