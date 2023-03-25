package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.validation.BookingDtoValid;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

import static ru.practicum.shareit.utilities.Constants.DATE_TIME_FORMAT;

/**
 * TODO Sprint add-bookings.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@BookingDtoValid(message = "Start must be earlier than end")
public class BookingDto {
    private Long id;
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    @FutureOrPresent
    private LocalDateTime start;
    @JsonFormat(pattern = DATE_TIME_FORMAT)
    @FutureOrPresent
    private LocalDateTime end;
    private Long itemId;
}
