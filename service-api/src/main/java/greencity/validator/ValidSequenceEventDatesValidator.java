package greencity.validator;

import greencity.annotations.ValidSequenceEventDates;
import greencity.dto.event.EventSaveDayInfoDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.List;

public class ValidSequenceEventDatesValidator implements ConstraintValidator<ValidSequenceEventDates, List<EventSaveDayInfoDto>> {
    /**
     * Validates a list of EventDayInfo objects to ensure that the start dates are in strictly ascending order.
     *
     * This method performs the following validation:
     * 1. Retrieves the start date of the first event in the list.
     * 2. Iterates through the list of EventDayInfo objects starting from the second event.
     * 3. For each event, checks if the current event's start date is after the previous event's start date.
     * 4. If any event's start date is not after the previous event's start date, the method returns false.
     * 5. If all events' start dates are in strictly ascending order, the method returns true.
     *
     * @param value   the list of EventDayInfo objects to be validated
     * @param context the context in which the constraint is evaluated
     * @return true if the start dates of all events are in strictly ascending order; false otherwise
     */
    @Override
    public boolean isValid(List<EventSaveDayInfoDto> value, ConstraintValidatorContext context) {
        LocalDate prevDate = null;
        if (value != null) {
            if (value.size() > 0) {
                prevDate = value.get(0).getStartDateTime().toLocalDate();
            }
            for (int i = 1; i < value.size(); i++) {
                LocalDate currentDate = value.get(i).getStartDateTime().toLocalDate();
                if (prevDate.isAfter(currentDate)) {
                    return false;
                }
                prevDate = currentDate;
            }
        }
        return true;
    }
}
