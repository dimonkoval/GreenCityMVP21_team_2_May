package greencity.validator;

import greencity.dto.event.EventSaveDayInfoDto;
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
class ValidSameDayValidatorTest {

    @InjectMocks
    private ValidSameDayValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void testValidSameDayEvent() {
        List<EventSaveDayInfoDto> dates = Arrays.asList(
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZonedDateTime.now().getZone()))
                        .endDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.of(23, 59), ZonedDateTime.now().getZone()))
                        .build()
        );
        assertTrue(validator.isValid(dates, context));
    }

    @Test
    void testValidSameDayEventWithDifferentTimes() {
        List<EventSaveDayInfoDto> dates = Arrays.asList(
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.of(9, 0), ZonedDateTime.now().getZone()))
                        .endDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.of(17, 0), ZonedDateTime.now().getZone()))
                        .build(),
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.of(18, 0), ZonedDateTime.now().getZone()))
                        .endDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.of(20, 0), ZonedDateTime.now().getZone()))
                        .build()
        );
        assertTrue(validator.isValid(dates, context));
    }

    @Test
    void testInvalidDifferentDaysEvent() {
        List<EventSaveDayInfoDto> dates = Arrays.asList(
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZonedDateTime.now().getZone()))
                        .endDateTime(ZonedDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(23, 59), ZonedDateTime.now().getZone()))
                        .build()
        );
        assertFalse(validator.isValid(dates, context));
    }

    @Test
    void testInvalidDifferentDaysEventWithMultipleEvents() {
        List<EventSaveDayInfoDto> dates = Arrays.asList(
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZonedDateTime.now().getZone()))
                        .endDateTime(ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(23, 59), ZonedDateTime.now().getZone()))
                        .build(),
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT, ZonedDateTime.now().getZone()))
                        .endDateTime(ZonedDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(23, 59), ZonedDateTime.now().getZone()))
                        .build()
        );
        assertFalse(validator.isValid(dates, context));
    }
}
