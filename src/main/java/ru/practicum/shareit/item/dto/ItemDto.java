package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;

/**
 * TODO Sprint add-controllers.
 */
@EqualsAndHashCode
public class ItemDto {
    @Getter @Setter
    private int id;
    private String name;
    private String description;
    private boolean available;
}
