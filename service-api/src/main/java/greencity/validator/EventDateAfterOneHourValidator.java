package greencity.validator;

import greencity.annotations.EventDateAfterOneHour;
import greencity.dto.event.model.EventDayInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventDateAfterOneHourValidator implements ConstraintValidator<EventDateAfterOneHour, List<EventDayInfo>> {
    @Override
    public boolean isValid(List<EventDayInfo> value, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();

        //first dateTime of event should be at least in one hour after now
        if (!value.get(0).getStartDateTime().toLocalDate().isAfter(today)) {
            if (value.get(0).getStartDateTime().toLocalDate().isBefore(today)) {
                return false;
            }
            int currentHour = LocalTime.now().getHour();
            if (currentHour >= value.get(0).getStartDateTime().toLocalTime().getHour()) {
                return false;
            }
        }

        return true;
    }
}
