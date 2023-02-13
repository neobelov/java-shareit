package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.exceptions.validation.PostInfo;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.storage.StorageObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class ItemDto {
    private Integer id;

    @NotNull(groups = PostInfo.class, message = "Item name can't be null")
    @NotEmpty(groups = PostInfo.class, message = "Item name can't be empty")
    @NotBlank(groups = PostInfo.class, message = "Item name can't be blank")
    private String name;

    @NotNull(groups = PostInfo.class, message = "Item description can't be null")
    @NotEmpty(groups = PostInfo.class, message = "Item description can't be empty")
    @NotBlank(groups = PostInfo.class, message = "Item description can't be blank")
    private String description;

    @NotNull(groups = PostInfo.class, message = "Item availability must be sent")
    private Boolean available;
}
