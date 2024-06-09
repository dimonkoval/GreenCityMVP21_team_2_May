package greencity.validator;

import greencity.dto.event.model.EventDayInfo;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ValidAllDayEventValidatorTest {

    @InjectMocks
    private ValidAllDayEventValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void testValidAllDayEvent() {
        List<EventDayInfo> dates = Arrays.asList(
                EventDayInfo.builder()
                        .startDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZonedDateTime.now().getZone()))
                        .endDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.of(23, 59), ZonedDateTime.now().getZone()))
                        .isAllDay(true)
                        .build()
        );
        assertTrue(validator.isValid(dates, context));
    }

    @Test
    void testInvalidAllDayEvent() {
        List<EventDayInfo> dates = Arrays.asList(
                EventDayInfo.builder()
                        .startDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.NOON, ZonedDateTime.now().getZone()))
                        .endDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.of(23, 59), ZonedDateTime.now().getZone()))
                        .isAllDay(true)
                        .build()
        );
        assertFalse(validator.isValid(dates, context));
    }

    @Test
    void testInvalidAllDayEventWithCustomHour() {
        List<EventDayInfo> dates = Arrays.asList(
                EventDayInfo.builder()
                        .startDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZonedDateTime.now().getZone()))
                        .endDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.NOON, ZonedDateTime.now().getZone()))
                        .isAllDay(true)
                        .build()
        );
        assertFalse(validator.isValid(dates, context));
    }

    @Test
    void testInvalidAllDayEventWithCustomHourss() {
        List<EventDayInfo> dates = Arrays.asList(
                EventDayInfo.builder()
                        .startDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.of(0, 10), ZonedDateTime.now().getZone()))
                        .endDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.of(15, 30), ZonedDateTime.now().getZone()))
                        .isAllDay(false)
                        .build()
        );
        assertTrue(validator.isValid(dates, context));
    }
}
