package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exceptions.NoRightsException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;

    public Item post(Item obj) {
        User user = userService.getById(obj.getOwner());
        return itemStorage.post(obj);
    }

    @Override
    public Item put(Item obj) {
        if (!Objects.equals(itemStorage.getById(obj.getId()).getOwner(), obj.getOwner())) {
            throw new NoRightsException(obj.getOwner() + " doesn't have rights to change this item");
        }
        return itemStorage.put(obj);
    }

    @Override
    public Item patch(Item obj) {
        Item item = itemStorage.getById(obj.getId());
        if (!Objects.equals(item.getOwner(), obj.getOwner())) {
            throw new NoRightsException(obj.getOwner() + " doesn't have rights to change this item");
        }
        item = item.patch(obj);
        itemStorage.put(item);
        return item;
    }

    @Override
    public Item deleteWithOwnerCheck(Integer id, Integer owner) {
        Item item = itemStorage.getById(id);
        if (!Objects.equals(item.getOwner(), owner)) {
            throw new NoRightsException(owner + " doesn't have rights to change this item");
        }
        return itemStorage.delete(id);
    }

    @Override
    public List<Item> getAll() {
        return itemStorage.getAll();
    }

    @Override
    public List<Item> getAllWithOwnerCheck(Integer owner) {
        return itemStorage.getAll().stream().filter(o-> Objects.equals(o.getOwner(), owner)).collect(Collectors.toList());
    }

    @Override
    public List<Item> searchItems(String text) {
        if (text.isEmpty() || text.isBlank()) {
            return Collections.emptyList();
        }
        return itemStorage.getAll().parallelStream()
                .filter(o->
                        (o.getName().toUpperCase().contains(text.toUpperCase()) || o.getDescription().toUpperCase().contains(text.toUpperCase()))
                        && o.getAvailable())
                .collect(Collectors.toList());
    }

    @Override
    public Item getById(Integer id) {
        return itemStorage.getById(id);
    }

    @Override
    public Item delete(Integer id) {
        return null;
    }

}
