package greencity.validator;

import greencity.annotations.ValidEventDateTime;
import greencity.dto.event.model.EventDayInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ValidEventDateTimeValidator implements ConstraintValidator<ValidEventDateTime, List<EventDayInfo>> {
    @Override
    public boolean isValid(List<EventDayInfo> value, ConstraintValidatorContext context) {
        if (value == null) { //event must have at list one dateTime
            return false;
        }
        if (value.get(0) == null) {
            return false;
        }
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

        //every next event date should be after each other
        for (int i = 1; i < value.size(); i++) {
            LocalDate prevDate = value.get(i - 1).getStartDateTime().toLocalDate();
            LocalDate currentDate = value.get(i).getStartDateTime().toLocalDate();
            if (currentDate == null) {
                for (int j = i + 1; j < value.size(); j++) {
                    if (value.get(j) != null) {
                        return false;
                    }
                }
                break;
            } else {
                if (prevDate.isAfter(currentDate) || prevDate.isEqual(currentDate)) {
                    return false;
                }
            }
        }

        //start time should be before end time, if event for all it should start at 00:00 and end at 23:59,
        // start and end date should be in one day
        for(EventDayInfo eventDateTime : value) {
            if (eventDateTime.getStartDateTime().isAfter(eventDateTime.getEndDateTime())
                    || eventDateTime.getStartDateTime().equals(eventDateTime.getEndDateTime())) {
                return false;
            }
            if (eventDateTime.isAllDay()) {
                if (eventDateTime.getStartDateTime().toLocalDate().isAfter(today)) {
                    if (eventDateTime.getStartDateTime().toLocalTime().isAfter(LocalTime.of(0, 0, 0))) {
                        return false;
                    }
                    if (eventDateTime.getEndDateTime().toLocalTime().isBefore(LocalTime.of(23, 59))) {
                        return false;
                    }
                }
            }
            if (!eventDateTime.getStartDateTime().toLocalDate().equals(eventDateTime.getEndDateTime().toLocalDate())) {
                return false;
            }
        }

        return true;
    }
}
