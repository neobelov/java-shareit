package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NoRightsException;
import ru.practicum.shareit.exceptions.ResourceNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;

    @Override
    public Item add(Item item) {
        Long owner = item.getOwner();
        if (!userService.existsById(owner)) {
            throw new ResourceNotFoundException("owner with id " + owner + "doesn't exist");
        }
        return itemRepository.save(item);
    }

    @Override
    public Item replace(Item item) {
        Item existingItem = getById(item.getId());
        if (!Objects.equals(existingItem.getOwner(), item.getOwner())) {
            throw new NoRightsException(item.getOwner() + " doesn't have rights to change this item");
        }
        return itemRepository.save(item);
    }

    @Override
    public Item update(Item item) {
        Item existingItem = getById(item.getId());
        if (!Objects.equals(existingItem.getOwner(), item.getOwner())) {
            throw new NoRightsException(existingItem.getOwner() + " doesn't have rights to change this item");
        }
        if (item.getName() != null) {
            existingItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            existingItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            existingItem.setAvailable(item.getAvailable());
        }
        return itemRepository.save(existingItem);
    }

    @Override
    public void deleteWithOwnerCheck(Long id, Long owner) {
        Item existingItem = getById(id);
        if (!Objects.equals(existingItem.getOwner(), owner)) {
            throw new NoRightsException(owner + " doesn't have rights to change this item");
        }
        itemRepository.deleteById(id);
    }

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getAllWithOwnerCheck(Long owner) {
        return itemRepository.findByOwnerEquals(owner);
    }

    @Override
    public List<Item> searchItems(String text) {
        if (text.isEmpty() || text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.searchItems(text);
    }

    @Override
    public Item getById(Long id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isEmpty()) {
            throw new ResourceNotFoundException("item with id " + id + " is not found");
        }
        return itemOptional.get();
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Boolean exists(Long id) {
        return itemRepository.existsById(id);
    }
}
