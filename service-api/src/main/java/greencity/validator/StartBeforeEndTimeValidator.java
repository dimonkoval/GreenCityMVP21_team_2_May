package greencity.validator;

import greencity.annotations.StartBeforeEndTime;
import greencity.dto.event.model.EventDayInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class StartBeforeEndTimeValidator implements ConstraintValidator<StartBeforeEndTime, List<EventDayInfo>> {
    @Override
    public boolean isValid(List<EventDayInfo> value, ConstraintValidatorContext context) {
        for(EventDayInfo eventDateTime : value) {
            if (eventDateTime.getStartDateTime().isAfter(eventDateTime.getEndDateTime())
                    || eventDateTime.getStartDateTime().equals(eventDateTime.getEndDateTime())) {
                return false;
            }
        }
        return true;
    }
}
