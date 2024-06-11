package greencity.validator;

import greencity.annotations.ValidLink;
import greencity.dto.event.EventSaveDayInfoDto;
import greencity.enums.EventStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidLinkValidator implements ConstraintValidator<ValidLink, List<EventSaveDayInfoDto>> {
    /**
     * Method that checks if all links included in received list of event day info is valid
     * (link cannot be null and should start with "http(s)://",
     * if status of day is offline link can not be set).
     */
    @Override
    public boolean isValid(List<EventSaveDayInfoDto> value, ConstraintValidatorContext context) {
        for (EventSaveDayInfoDto day: value) {
            if (day.getStatus().equals(EventStatus.ONLINE_OFFLINE) || day.getStatus().equals(EventStatus.ONLINE)) {
                if (day.getLink() == null) {
                    return false;
                }
                if (!day.getLink().startsWith("http://") && !day.getLink().startsWith("https://")) {
                    return false;
                }
            } else if (day.getLink() != null) {
                return false;
            }
        }
        return true;
    }
}
