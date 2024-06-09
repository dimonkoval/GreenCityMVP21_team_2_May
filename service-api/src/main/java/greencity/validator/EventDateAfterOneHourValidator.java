package greencity.validator;

import greencity.annotations.EventDateAfterOneHour;
import greencity.dto.event.model.EventDayInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.ZonedDateTime;
import java.util.List;

public class EventDateAfterOneHourValidator implements ConstraintValidator<EventDateAfterOneHour, List<EventDayInfo>> {
    @Override
    public boolean isValid(List<EventDayInfo> value, ConstraintValidatorContext context) {
        //first dateTime of event should be at least in one hour after now
        ZonedDateTime nowPlusOneHour = ZonedDateTime.now().plusHours(1);
        if (value.get(0).getStartDateTime().isBefore(nowPlusOneHour)) {
            return false;
        }

        return true;
    }
}
