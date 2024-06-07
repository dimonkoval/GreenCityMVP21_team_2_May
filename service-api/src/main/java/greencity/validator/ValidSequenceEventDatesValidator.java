package greencity.validator;

import greencity.annotations.ValidSequenceEventDates;
import greencity.dto.event.model.EventDayInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.List;

public class ValidSequenceEventDatesValidator implements ConstraintValidator<ValidSequenceEventDates, List<EventDayInfo>> {
    @Override
    public boolean isValid(List<EventDayInfo> value, ConstraintValidatorContext context) {
        LocalDate prevDate = value.get(0).getStartDateTime().toLocalDate();
        for (int i = 1; i < value.size(); i++) {
            LocalDate currentDate = value.get(i).getStartDateTime().toLocalDate();
            if (prevDate.isAfter(currentDate) || prevDate.isEqual(currentDate)) {
                return false;
            }
            prevDate = currentDate;
        }
        return true;
    }
}
