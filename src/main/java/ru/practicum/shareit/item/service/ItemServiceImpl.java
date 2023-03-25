package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exceptions.CommentNoBookingException;
import ru.practicum.shareit.exceptions.NoRightsException;
import ru.practicum.shareit.exceptions.ResourceNotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.dto.ItemWithBookings;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.CommentRepository;
import ru.practicum.shareit.item.storage.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ItemMapper itemMapper;
    private final CommentMapper commentMapper;

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
    public void delete(Long id, Long owner) {
        Item existingItem = getById(id);
        if (!Objects.equals(existingItem.getOwner(), owner)) {
            throw new NoRightsException(owner + " doesn't have rights to change this item");
        }
        itemRepository.deleteById(id);
    }

    @Override
    public List<ItemWithBookings> getAll(Long owner) {
        List<Item> items = itemRepository.findByOwnerEqualsOrderById(owner);
        return items.parallelStream().map(i -> getWithBookingsById(i.getId(),owner)).collect(Collectors.toList());
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
    public ItemWithBookings getWithBookingsById(Long id, Long ownerId) {
        Item item = getById(id);
        if (!Objects.equals(item.getOwner(), ownerId)) {
            return itemMapper.mapToItemWithBookings(
                    item,
                    new Booking(),
                    new Booking(),
                    commentRepository
                            .findByItemId(id)
                            .parallelStream()
                            .map(commentMapper::mapToCommentDto)
                            .collect(Collectors.toList()));
        }
        Optional<Booking> lastBooking = bookingRepository.findLastBooking(id, LocalDateTime.now());
        Optional<Booking> nextBooking = bookingRepository.findNextBooking(id, LocalDateTime.now());
        return itemMapper.mapToItemWithBookings(
                item,
                lastBooking.orElseGet(Booking::new),
                nextBooking.orElseGet(Booking::new),
                commentRepository
                        .findByItemId(id)
                        .parallelStream()
                        .map(commentMapper::mapToCommentDto)
                        .collect(Collectors.toList()));
    }

    @Override
    public CommentDto addComment(Long itemId, Long bookerId, CommentDto dto) {
        List<Booking> bookings = bookingRepository.findByBookerIdAndItemIdAndStatusAndStartLessThanEqual(bookerId, itemId, BookingStatus.APPROVED, LocalDateTime.now());
        if (bookings.isEmpty()) {
            throw new CommentNoBookingException("You can't comment on item " + itemId + " if you haven't booked it");
        }
        Booking booking = bookings.get(0);
        Comment comment = commentMapper.mapToComment(dto, booking.getItem(), booking.getBooker());
        return commentMapper.mapToCommentDto(
                commentRepository.save(comment));
    }
}
