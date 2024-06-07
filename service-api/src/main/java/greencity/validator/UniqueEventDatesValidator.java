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
