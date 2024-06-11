package greencity.validator;

import greencity.annotations.ValidSameDay;
import greencity.dto.event.EventSaveDayInfoDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.List;

public class ValidSameDayValidator implements ConstraintValidator<ValidSameDay, List<EventSaveDayInfoDto>> {
    /**
     * Validates a list of EventSaveDayInfoDto objects to ensure that each event's startDateTime and endDateTime
     * are on the same day.
     *
     * @param value The list of EventSaveDayInfoDto objects to be validated.
     * @param constraintValidatorContext The context in which the constraint is evaluated.
     * @return true if all events in the list have their startDateTime and endDateTime on the same day,
     *         false otherwise.
     */
    @Override
    public boolean isValid(List<EventSaveDayInfoDto> value, ConstraintValidatorContext constraintValidatorContext) {

        for ( EventSaveDayInfoDto eventSaveDayInfoDto : value ) {
            LocalDate startDate = eventSaveDayInfoDto.getStartDateTime().toLocalDate();
            LocalDate endDate = eventSaveDayInfoDto.getEndDateTime().toLocalDate();
            if (!startDate.equals(endDate)) {
                return false;
            }
        }

        return true;
    }
}
