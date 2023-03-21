package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.validation.PostInfo;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.dto.ItemWithBookings;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.utilities.Constants;

import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper = new ItemMapper();

    @GetMapping
    public List<ItemWithBookings> getAll(@RequestHeader(Constants.SHARER_USER_ID_HTTP_HEADER) Long ownerId) {
        return itemService.getAll(ownerId);
    }

    @GetMapping("/{itemId}")
    public ItemWithBookings getById(@RequestHeader(Constants.SHARER_USER_ID_HTTP_HEADER) Long ownerId, @PathVariable Long itemId) {
        return itemService.getWithBookingsById(itemId, ownerId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestParam String text) {
        return itemService.searchItems(text).parallelStream().map(itemMapper::mapToItemDto).collect(Collectors.toList());
    }

    @PostMapping
    public ItemDto post(@RequestHeader(Constants.SHARER_USER_ID_HTTP_HEADER) Long owner, @Validated({PostInfo.class, Default.class}) @RequestBody ItemDto itemDto) {
        Item item = itemMapper.mapToItem(itemDto, owner);
        return itemMapper.mapToItemDto(itemService.add(item));
    }

    @PatchMapping("/{itemId}")
    public ItemDto patch(@RequestHeader(Constants.SHARER_USER_ID_HTTP_HEADER) Long owner, @Validated(Default.class) @RequestBody ItemDto itemDto, @PathVariable Long itemId) {
        itemDto.setId(itemId);
        Item item = itemMapper.mapToItem(itemDto, owner);
        return itemMapper.mapToItemDto(itemService.update(item));
    }

    @DeleteMapping("/{itemId}")
    public void delete(@PathVariable Long itemId, @RequestHeader(Constants.SHARER_USER_ID_HTTP_HEADER) Long owner) {
        itemService.delete(itemId, owner);
    }
}
