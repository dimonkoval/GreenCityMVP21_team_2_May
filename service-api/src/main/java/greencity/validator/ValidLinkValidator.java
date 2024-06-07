package greencity.validator;

import greencity.annotations.ValidLink;
import greencity.dto.event.EventSaveDayInfoDto;
import greencity.dto.event.model.EventStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidLinkValidator implements ConstraintValidator<ValidLink, List<EventSaveDayInfoDto>> {
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
