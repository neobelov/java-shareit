package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.storage.BookingRepository;
import ru.practicum.shareit.exceptions.ChangeApprovedBookingStatusException;
import ru.practicum.shareit.exceptions.ResourceNotFoundException;
import ru.practicum.shareit.exceptions.UnavailableItemException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final UserService userService;
    private final BookingMapper bookingMapper = new BookingMapper();
    @Override
    public Booking add(BookingDto dto, Long bookerId) {
        Booking booking = bookingMapper.mapToBooking(dto);
        Item item = itemService.getById(dto.getItemId());
        if (Objects.equals(item.getOwner(), bookerId)) {
            throw new ResourceNotFoundException("you can't book your own item");
        }
        if (!item.getAvailable()) {
            throw new UnavailableItemException("you can't book unavailable item");
        }
        booking.setItem(item);
        booking.setBooker(userService.getById(bookerId));
        booking.setStatus(BookingStatus.WAITING);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking approveBooking(Long ownerId, Long bookingId, Boolean isApproved) {
        Optional<Booking> bookingOptional = bookingRepository.findByIdAndItemOwner(bookingId, ownerId);
        if (bookingOptional.isEmpty()) {
            throw new ResourceNotFoundException(ownerId + " doesn't have this item");
        }
        Booking booking = bookingOptional.get();
        if (booking.getStatus() == BookingStatus.APPROVED) {
            throw new ChangeApprovedBookingStatusException("you can't change approved booking status");
        }
        if (isApproved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return bookingRepository.save(booking);
    }

    @Override
    public Booking get(Long userId, Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException("booking with id " + bookingId + " is not found");
        }
        Optional<Booking> bookingOptional = bookingRepository.findByIdAndBookerOrOwner(bookingId, userId);
        if (bookingOptional.isEmpty()) {
            throw new ResourceNotFoundException(userId + " doesn't have this booking");
        }
        return bookingOptional.get();
    }

    @Override
    public List<Booking> getBookings(Long bookerId, BookingState state) {
        User booker = userService.getById(bookerId);
        switch (state) {
            case CURRENT:
                return bookingRepository.findCurrentByBookerId(bookerId, LocalDateTime.now());
            case PAST:
                return bookingRepository.findByBookerIdAndEndBeforeOrderByStartDesc(bookerId,LocalDateTime.now());
            case FUTURE:
                return bookingRepository.findByBookerIdAndStartAfterOrderByStartDesc(bookerId, LocalDateTime.now());
            case WAITING:
                return bookingRepository.findByBookerIdAndStatusOrderByStartDesc(bookerId, BookingStatus.WAITING);
            case REJECTED:
                return bookingRepository.findByBookerIdAndStatusOrderByStartDesc(bookerId, BookingStatus.REJECTED);
            default:
                return bookingRepository.findByBookerIdOrderByStartDesc(bookerId);
        }
    }

    @Override
    public List<Booking> getOwnerBookings(Long ownerId, BookingState state) {
        User owner = userService.getById(ownerId);
        switch (state) {
            case CURRENT:
                return bookingRepository.findCurrentByOwnerId(ownerId, LocalDateTime.now());
            case PAST:
                return bookingRepository.findByItemOwnerAndEndBeforeOrderByStartDesc(ownerId,LocalDateTime.now());
            case FUTURE:
                return bookingRepository.findByItemOwnerAndStartAfterOrderByStartDesc(ownerId, LocalDateTime.now());
            case WAITING:
                return bookingRepository.findByItemOwnerAndStatusOrderByStartDesc(ownerId, BookingStatus.WAITING);
            case REJECTED:
                return bookingRepository.findByItemOwnerAndStatusOrderByStartDesc(ownerId, BookingStatus.REJECTED);
            default:
                return bookingRepository.findByItemOwnerOrderByStartDesc(ownerId);
        }
    }
}
