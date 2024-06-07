package greencity.validator;

import greencity.annotations.NotEmptyEventDateTime;
import greencity.dto.event.model.EventDayInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NotEmptyEventDateTimeValidator implements ConstraintValidator<NotEmptyEventDateTime, List<EventDayInfo>> {
    @Override
    public boolean isValid(List<EventDayInfo> value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty() && value.getFirst() != null;
    }
}
