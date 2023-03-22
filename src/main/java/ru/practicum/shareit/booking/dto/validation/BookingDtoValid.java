package ru.practicum.shareit.booking.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BookingDtoValidator.class)
public @interface BookingDtoValid {
    String message() default "Booking dto is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}