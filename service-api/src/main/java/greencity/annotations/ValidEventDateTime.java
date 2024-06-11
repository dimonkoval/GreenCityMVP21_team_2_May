package greencity.annotations;

import greencity.validator.ValidEventDateTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotEmptyEventDateTime(message = "Please, enter at least one dateTime for Event")
@EventDateAfterOneHour(message = "First Event should be at least one hour after now")
@ValidSequenceEventDates(message = "Each event date must follow the previous one")
@StartBeforeEndTime(message = "End time cannot be before Start time")
@ValidAllDayEvent(message = "An all-day event should begin at 00:00 and conclude at 23:59")
@ValidSameDay(message = "StartDate and EndDate must be the same day")
@UniqueEventDates
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEventDateTimeValidator.class)
public @interface ValidEventDateTime {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
