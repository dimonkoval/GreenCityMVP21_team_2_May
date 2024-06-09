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
class StartBeforeEndTimeTest {

    @InjectMocks
    private StartBeforeEndTimeValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void testValidStartBeforeEndTime() {
        List<EventSaveDayInfoDto> eventDays = new ArrayList<>();
        eventDays.add(EventSaveDayInfoDto.builder()
                .startDateTime(ZonedDateTime.now())
                .endDateTime(ZonedDateTime.now().plusHours(1))
                .isAllDay(false)
                .build());
        assertTrue(validator.isValid(eventDays, context));
    }

    @Test
    void testInvalidStartAfterEndTime() {
        List<EventSaveDayInfoDto> eventDays = new ArrayList<>();
        eventDays.add(EventSaveDayInfoDto.builder()
                .startDateTime(ZonedDateTime.now().plusHours(2))
                .endDateTime(ZonedDateTime.now())
                .isAllDay(false)
                .build());
        assertFalse(validator.isValid(eventDays, context));
    }

    @Test
    void testInvalidStartEqualEndTime() {
        List<EventSaveDayInfoDto> eventDays = new ArrayList<>();
        eventDays.add(EventSaveDayInfoDto.builder()
                .startDateTime(ZonedDateTime.now())
                .endDateTime(ZonedDateTime.now())
                .isAllDay(false)
                .build());
        assertFalse(validator.isValid(eventDays, context));
    }
}
