package greencity.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@NotEmptyEventDateTime(message = "Please, enter at least one dateTime for Event")
@EventDateAfterOneHour(message = "First Event should be at least one hour after now")
@ValidSequenceEventDates(message = "Each event date must follow the previous one")
@StartBeforeEndTime(message = "End time cannot be before Start time")
@ValidAllDayEvent(message = "An all-day event should begin at 00:00 and conclude at 23:59")
@ValidSameDay(message = "StartDate and EndDate must be the same day")
@UniqueEventDates
@ValidAddress(message = "Please add address to the event.")
@ValidLink(message = "Please add a link to the event. The link must start with http(s)://")
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface ValidEventDayInfo {
    String message() default "Day info is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
