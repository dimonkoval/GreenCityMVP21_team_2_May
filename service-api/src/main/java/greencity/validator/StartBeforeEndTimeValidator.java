package greencity.validator;

import greencity.annotations.StartBeforeEndTime;
import greencity.dto.event.EventSaveDayInfoDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class StartBeforeEndTimeValidator implements ConstraintValidator<StartBeforeEndTime, List<EventSaveDayInfoDto>> {
    /**
     * Validates a list of EventDayInfo objects to ensure that each event's start time is before its end time.
     *
     * This method performs the following validation for each event in the list:
     * 1. Checks if the start time of the event is after the end time.
     * 2. Checks if the start time of the event is equal to the end time.
     * 3. If either condition is true for any event, the method returns false.
     * 4. If all events have a start time that is before their end time, the method returns true.
     *
     * @param value   the list of EventDayInfo objects to be validated
     * @param context the context in which the constraint is evaluated
     * @return true if each event's start time is before its end time; false otherwise
     */
    @Override
    public boolean isValid(List<EventSaveDayInfoDto> value, ConstraintValidatorContext context) {
        for(EventSaveDayInfoDto eventDateTime : value) {
            if (eventDateTime.getStartDateTime().isAfter(eventDateTime.getEndDateTime())
                    || eventDateTime.getStartDateTime().equals(eventDateTime.getEndDateTime())) {
                return false;
            }
        }
        return true;
    }
}
