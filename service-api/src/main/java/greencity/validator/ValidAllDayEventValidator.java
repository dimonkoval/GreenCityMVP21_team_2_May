package greencity.validator;

import greencity.annotations.ValidAllDayEvent;
import greencity.dto.event.EventSaveDayInfoDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.util.List;

public class ValidAllDayEventValidator implements ConstraintValidator<ValidAllDayEvent, List<EventSaveDayInfoDto>> {
    /**
     * Validates a list of EventDayInfo objects to ensure that all-day events start at the beginning of the day and end at the end of the day.
     *
     * This method performs the following validation for each event in the list:
     * 1. Checks if the event is marked as an all-day event.
     * 2. If the event is an all-day event, it verifies:
     *    2.1. The start time is exactly at the beginning of the day (00:00).
     *    2.2. The end time is exactly at the end of the day (23:59).
     * 3. If either condition is violated for any all-day event, the method returns false.
     * 4. If all all-day events meet the criteria, the method returns true.
     *
     * @param value   the list of EventDayInfo objects to be validated
     * @param context the context in which the constraint is evaluated
     * @return true if all all-day events start at 00:00 and end at 23:59; false otherwise
     */
    @Override
    public boolean isValid(List<EventSaveDayInfoDto> value, ConstraintValidatorContext context) {
        for (EventSaveDayInfoDto eventDateTime : value) {
            if (eventDateTime.isAllDay()) {
                if (eventDateTime.getStartDateTime().toLocalTime().isAfter(LocalTime.of(0, 0))) {
                    return false;
                }
                if (eventDateTime.getEndDateTime().toLocalTime().isBefore(LocalTime.of(23, 59))) {
                    return false;
                }
            }
        }
        return true;
    }
}
