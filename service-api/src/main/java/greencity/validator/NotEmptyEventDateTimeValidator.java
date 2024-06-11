package greencity.validator;

import greencity.annotations.NotEmptyEventDateTime;
import greencity.dto.event.EventSaveDayInfoDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NotEmptyEventDateTimeValidator implements ConstraintValidator<NotEmptyEventDateTime, List<EventSaveDayInfoDto>> {
    /**
     * Validates a list of EventDayInfo objects.
     *
     * This method checks if the given list of EventDayInfo objects is valid by ensuring:
     * 1. The list is not null.
     * 2. The list is not empty.
     * 3. The first element of the list is not null.
     *
     * @param value   the list of EventDayInfo objects to be validated
     * @param context the context in which the constraint is evaluated
     * @return true if the list is valid according to the above criteria; false otherwise
     */
    @Override
    public boolean isValid(List<EventSaveDayInfoDto> value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty() && value.getFirst() != null;
    }
}
