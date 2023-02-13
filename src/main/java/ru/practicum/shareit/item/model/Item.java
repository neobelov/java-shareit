package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.storage.StorageObject;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item extends ItemDto implements StorageObject<Item> {
    private Integer owner;
    public Item patch(Item item) {
        if (item.getId() != null) {
            this.setId(item.getId());
        }
        if (item.getName() != null) {
            this.setName(item.getName());
        }
        if (item.getDescription() != null) {
            this.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            this.setAvailable(item.getAvailable());
        }
        return this;
    }
}
