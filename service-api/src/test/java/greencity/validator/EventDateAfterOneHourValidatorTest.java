package greencity.validator;

import greencity.dto.event.model.EventDayInfo;
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
class EventDateAfterOneHourValidatorTest {

    @InjectMocks
    private EventDateAfterOneHourValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void testFirstDateTimeInPast() {
        List<EventDayInfo> eventDays = new ArrayList<>();
        eventDays.add(EventDayInfo.builder()
                .startDateTime(ZonedDateTime.now().minusDays(1))
                .endDateTime(ZonedDateTime.now().minusDays(1).plusHours(1))
                .isAllDay(false)
                .build());
        assertFalse(validator.isValid(eventDays, context));
    }

    @Test
    void testFirstDateTimeLessThanAnHourAhead() {
        List<EventDayInfo> eventDays = new ArrayList<>();
        eventDays.add(EventDayInfo.builder()
                .startDateTime(ZonedDateTime.now().plusMinutes(30))
                .endDateTime(ZonedDateTime.now().plusHours(3))
                .isAllDay(false)
                .build());
        assertFalse(validator.isValid(eventDays, context));
    }

    @Test
    void testFirstDateTimeExactlyAnHourAhead() {
        List<EventDayInfo> eventDays = new ArrayList<>();
        eventDays.add(EventDayInfo.builder()
                .startDateTime(ZonedDateTime.now().plusHours(1))
                .endDateTime(ZonedDateTime.now().plusHours(2))
                .isAllDay(false)
                .build());
        assertTrue(validator.isValid(eventDays, context));
    }

    @Test
    void testFirstDateTimeMoreThanAnHourAhead() {
        List<EventDayInfo> eventDays = new ArrayList<>();
        eventDays.add(EventDayInfo.builder()
                .startDateTime(ZonedDateTime.now().plusHours(1))
                .endDateTime(ZonedDateTime.now().plusHours(3))
                .isAllDay(false)
                .build());
        assertTrue(validator.isValid(eventDays, context));
    }

    @Test
    void testFirstDateTimeTodayButPastCurrentHour() {
        List<EventDayInfo> eventDays = new ArrayList<>();
        eventDays.add(EventDayInfo.builder()
                .startDateTime(ZonedDateTime.now().minusHours(1))
                .endDateTime(ZonedDateTime.now().plusHours(1))
                .isAllDay(false)
                .build());
        assertFalse(validator.isValid(eventDays, context));
    }

    @Test
    void testFirstDateTimeTodayButWithinNextHour() {
        List<EventDayInfo> eventDays = new ArrayList<>();
        eventDays.add(EventDayInfo.builder()
                .startDateTime(ZonedDateTime.now().plusMinutes(10))
                .endDateTime(ZonedDateTime.now().plusHours(1))
                .isAllDay(false)
                .build());
        assertFalse(validator.isValid(eventDays, context));
    }

    @Test
    void testFirstDateTimeTomorrow() {
        List<EventDayInfo> eventDays = new ArrayList<>();
        eventDays.add(EventDayInfo.builder()
                .startDateTime(ZonedDateTime.now().plusDays(1).withHour(10))
                .endDateTime(ZonedDateTime.now().plusHours(1).withHour(15))
                .isAllDay(false)
                .build());
        assertTrue(validator.isValid(eventDays, context));
    }

    @Test
    void testMultipleValidEventDays() {
        List<EventDayInfo> eventDays = new ArrayList<>();
        eventDays.add(EventDayInfo.builder()
                .startDateTime(ZonedDateTime.now().plusHours(2))
                .endDateTime(ZonedDateTime.now().plusHours(3))
                .isAllDay(false)
                .build());
        eventDays.add(EventDayInfo.builder()
                .startDateTime(ZonedDateTime.now().plusDays(1).withHour(10))
                .endDateTime(ZonedDateTime.now().plusDays(1).withHour(18))
                .isAllDay(false)
                .build());
        assertTrue(validator.isValid(eventDays, context));
    }

    @Test
    void testMultipleEventDaysWithFirstInvalid() {
        List<EventDayInfo> eventDays = new ArrayList<>();
        eventDays.add(EventDayInfo.builder()
                .startDateTime(ZonedDateTime.now().plusMinutes(10))
                .endDateTime(ZonedDateTime.now().plusHours(3))
                .isAllDay(false)
                .build());
        eventDays.add(EventDayInfo.builder()
                .startDateTime(ZonedDateTime.now().plusDays(1).withHour(10))
                .endDateTime(ZonedDateTime.now().plusDays(1).withHour(18))
                .isAllDay(false)
                .build());
        assertFalse(validator.isValid(eventDays, context));
    }
}
