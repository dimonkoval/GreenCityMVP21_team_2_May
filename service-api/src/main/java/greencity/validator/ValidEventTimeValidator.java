package greencity.validator;

import greencity.annotations.ValidEventDateTime;
import greencity.dto.event.EventDateTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ValidEventTimeValidator implements ConstraintValidator<ValidEventDateTime, List<EventDateTime>> {
    @Override
    public boolean isValid(List<EventDateTime> value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value.get(0) == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        if (!value.get(0).getDate().isAfter(today)) {
            if (value.get(0).getDate().isBefore(today)) {
                return false;
            }
            int currentHour = LocalTime.now().getHour();
            if (currentHour >= value.get(0).getStartTime().getHour()) {
                return false;
            }
        }
        for(EventDateTime eventDateTime : value) {
            if (eventDateTime.getStartTime().isAfter(eventDateTime.getEndTime())
                    || eventDateTime.getStartTime().equals(eventDateTime.getEndTime())) {
                return false;
            }
            if (eventDateTime.isAllDay()) {
                if (eventDateTime.getDate().isAfter(today)) {
                    if (eventDateTime.getStartTime().isAfter(LocalTime.of(0, 0, 0))) {
                        return false;
                    }
                    if (eventDateTime.getEndTime().isBefore(LocalTime.of(23, 59))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
