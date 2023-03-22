package ru.practicum.shareit.booking.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerIdOrderByStartDesc(Long bookerId);
    List<Booking> findByBookerIdAndEndBeforeOrderByStartDesc(Long bookerId, LocalDateTime now);
    List<Booking> findByBookerIdAndStartAfterOrderByStartDesc(Long bookerId,LocalDateTime now);
    List<Booking> findByBookerIdAndStatusOrderByStartDesc(Long bookerId,BookingStatus status);
    List<Booking> findByItemOwnerOrderByStartDesc(Long ownerId);
    List<Booking> findByItemOwnerAndEndBeforeOrderByStartDesc(Long ownerId, LocalDateTime now);
    List<Booking> findByItemOwnerAndStartAfterOrderByStartDesc(Long ownerId,LocalDateTime now);
    List<Booking> findByItemOwnerAndStatusOrderByStartDesc(Long ownerId,BookingStatus status);

    @Query("SELECT bk " +
            "FROM Booking bk " +
            "WHERE bk.start <= :now AND bk.end >= :now AND bk.booker.id = :bookerId " +
            "ORDER BY bk.id ASC")
    List<Booking> findCurrentByBookerId(Long bookerId, LocalDateTime now);

    @Query("SELECT bk " +
            "FROM Booking bk " +
            "WHERE bk.start <= :now AND bk.end >= :now AND bk.item.owner = :ownerId " +
            "ORDER BY bk.id ASC")
    List<Booking> findCurrentByOwnerId(Long ownerId, LocalDateTime now);

    Optional<Booking> findByIdAndItemOwner(Long bookingId, Long ownerId);

    @Query(" SELECT bk " +
            "FROM Booking bk " +
            "WHERE bk.id = :bookingId AND (bk.item.owner = :userId or bk.booker.id = :userId)")
    Optional<Booking> findByIdAndBookerOrOwner(Long bookingId, Long userId);

    @Query("SELECT bk " +
            "FROM Booking bk " +
            "WHERE bk.item.id = :itemId AND bk.start <= :now " +
            "AND bk.status <> 'REJECTED' " +
            "AND bk.end IN (SELECT MAX(bk1.end) " +
            "               FROM Booking bk1 " +
            "               WHERE bk1.item.id = :itemId " +
            "               AND bk1.start <= :now)")
    Optional<Booking> findLastBooking(Long itemId, LocalDateTime now);
    @Query("SELECT bk " +
            "FROM Booking bk " +
            "WHERE bk.item.id = :itemId " +
            "AND bk.status <> 'REJECTED' " +
            "AND bk.start IN (SELECT MIN(bk1.start) " +
            "               FROM Booking bk1 " +
            "               WHERE bk1.item.id = :itemId " +
            "               AND bk1.start >= :now)")
    Optional<Booking> findNextBooking(Long itemId, LocalDateTime now);
    List<Booking> findByBookerIdAndItemIdAndStatusAndStartLessThanEqual(Long bookerId, Long itemId, BookingStatus status, LocalDateTime now);
}
