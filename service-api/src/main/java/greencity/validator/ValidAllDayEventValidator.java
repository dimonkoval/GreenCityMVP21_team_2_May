package greencity.validator;

import greencity.annotations.ValidAllDayEvent;
import greencity.dto.event.model.EventDayInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.util.List;

public class ValidAllDayEventValidator implements ConstraintValidator<ValidAllDayEvent, List<EventDayInfo>> {
    @Override
    public boolean isValid(List<EventDayInfo> value, ConstraintValidatorContext context) {
        for (EventDayInfo eventDateTime : value) {
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
