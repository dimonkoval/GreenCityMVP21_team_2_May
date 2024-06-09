package greencity.validator;

import greencity.dto.event.EventSaveDayInfoDto;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class NotEmptyEventDateTimeValidatorTest {

    @InjectMocks
    private NotEmptyEventDateTimeValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void testNullValue() {
        assertFalse(validator.isValid(null, context));
    }

    @Test
    void testEmptyList() {
        List<EventSaveDayInfoDto> eventDays = new ArrayList<>();
        assertFalse(validator.isValid(eventDays, context));
    }

    @Test
    void testFirstElemIsNull() {
        List<EventSaveDayInfoDto> eventDays = new ArrayList<>();
        eventDays.add(null);
        assertFalse(validator.isValid(eventDays, context));
    }

    @Test
    void testValid() {
        List<EventSaveDayInfoDto> eventDays = new ArrayList<>();
        eventDays.add(EventSaveDayInfoDto.builder()
                .startDateTime(ZonedDateTime.now().plusDays(1))
                .endDateTime(ZonedDateTime.now().plusDays(1).plusHours(2))
                .isAllDay(false)
                .build());
        assertTrue(validator.isValid(eventDays, context));
    }

}
