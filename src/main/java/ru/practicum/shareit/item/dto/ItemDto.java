package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.exceptions.validation.PostInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */

@Data
@AllArgsConstructor
public class ItemDto {
    private Long id;

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
