package greencity.validator;

import greencity.annotations.*;
import greencity.dto.event.EventSaveDayInfoDto;
import greencity.dto.event.model.EventDayInfo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ValidEventDateTimeValidator implements ConstraintValidator<ValidEventDateTime, List<EventSaveDayInfoDto>> {
    @Override
    public boolean isValid(List<EventSaveDayInfoDto> value, ConstraintValidatorContext context) {

        if (!new NotEmptyEventDateTimeValidator().isValid(value, context)) {
            return false;
        }

        if (!new EventDateAfterOneHourValidator().isValid(value, context)) {
            return false;
        }

        if (!new ValidSequenceEventDatesValidator().isValid(value, context)) {
            return false;
        }
        if (!new StartBeforeEndTimeValidator().isValid(value, context)) {
            return false;
        }
        if (!new ValidAllDayEventValidator().isValid(value, context)) {
            return false;
        }

        if (!new ValidSameDayValidator().isValid(value, context)) {
            return false;
        }

        if (!new UniqueEventDatesValidator().isValid(value, context)) {
            return false;
        }



        return true;
    }
}
