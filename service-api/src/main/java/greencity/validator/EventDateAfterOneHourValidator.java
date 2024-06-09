package greencity.validator;

import greencity.annotations.EventDateAfterOneHour;
import greencity.dto.event.EventSaveDayInfoDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.ZonedDateTime;
import java.util.List;

public class EventDateAfterOneHourValidator implements ConstraintValidator<EventDateAfterOneHour, List<EventSaveDayInfoDto>> {
    /**
     * Validates a list of EventDayInfo objects to ensure the first event's start time is at least one hour
     * from the current time.
     *
     * This method performs the following validation:
     * 1. Retrieves the current date and time plus one hour.
     * 2. Checks if the start time of the first event in the list is before this calculated time.
     * 3. If the start time of the first event is before the current time plus one hour, the method returns false.
     * 4. Otherwise, the method returns true.
     *
     * @param value   the list of EventDayInfo objects to be validated
     * @param context the context in which the constraint is evaluated
     * @return true if the start time of the first event is at least one hour from now; false otherwise
     */
    @Override
    public boolean isValid(List<EventSaveDayInfoDto> value, ConstraintValidatorContext context) {
        //first dateTime of event should be at least in one hour after now
        ZonedDateTime nowPlusOneHour = ZonedDateTime.now().plusHours(1);
        if (value.get(0).getStartDateTime().isBefore(nowPlusOneHour)) {
            return false;
        }

        return true;
    }
}
