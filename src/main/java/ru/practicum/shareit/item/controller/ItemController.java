package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exceptions.validation.PostInfo;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
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
    public List<ItemDto> getAllWithOwnerCheck(@RequestHeader(Constants.SHARER_USER_ID_HTTP_HEADER) Long owner) {
        return itemService.getAllWithOwnerCheck(owner).parallelStream().map(itemMapper::mapToItemDto).collect(Collectors.toList());
    }

    @GetMapping("/{itemId}")
    public ItemDto getById(@PathVariable Long itemId) {
        return itemMapper.mapToItemDto(itemService.getById(itemId));
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
        itemService.deleteWithOwnerCheck(itemId, owner);
    }
}
