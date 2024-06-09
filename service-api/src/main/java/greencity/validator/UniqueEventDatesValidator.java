package greencity.validator;

import greencity.annotations.UniqueEventDates;
import greencity.dto.event.model.EventDayInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueEventDatesValidator implements ConstraintValidator<UniqueEventDates, List<EventDayInfo>> {
    /**
     * Validates a list of EventDayInfo objects to ensure no duplicate start dates.
     *
     * This method performs the following validation:
     * 1. Initializes an empty set to keep track of unique start dates.
     * 2. Iterates through the list of EventDayInfo objects.
     * 3. For each event, retrieves the start date (ignoring the time).
     * 4. Attempts to add the start date to the set.
     * 5. If the start date is already present in the set (indicating a duplicate), the method returns false.
     * 6. If all start dates are unique, the method returns true.
     *
     * @param value   the list of EventDayInfo objects to be validated
     * @param context the context in which the constraint is evaluated
     * @return true if all events have unique start dates; false otherwise
     */
    @Override
    public boolean isValid(List<EventDayInfo> value, ConstraintValidatorContext context) {
        Set<LocalDate> dates = new HashSet<>();
        for (EventDayInfo eventDayInfo : value) {
            LocalDate startDate = eventDayInfo.getStartDateTime().toLocalDate();
            if (!dates.add(startDate)) {
                return false;
            }
        }
        return true;
    }
}
