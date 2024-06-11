package greencity.validator;

import greencity.dto.event.EventSaveDayInfoDto;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ValidSequenceEventDatesValidatorTest {

    @InjectMocks
    private ValidSequenceEventDatesValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void testValidSequenceEventDates() {
        List<EventSaveDayInfoDto> eventDays = Arrays.asList(
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.now().plusDays(1))
                        .endDateTime(ZonedDateTime.now().plusDays(1).plusHours(2))
                        .build(),
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.now().plusDays(2))
                        .endDateTime(ZonedDateTime.now().plusDays(2).plusHours(2))
                        .build()
        );
        assertTrue(validator.isValid(eventDays, context));
    }

    @Test
    void testInvalidSequenceEventDates() {
        List<EventSaveDayInfoDto> eventDays = Arrays.asList(
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.now().plusDays(2))
                        .endDateTime(ZonedDateTime.now().plusDays(2).plusHours(2))
                        .build(),
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.now().plusDays(1))
                        .endDateTime(ZonedDateTime.now().plusDays(1).plusHours(2))
                        .build()
        );
        assertFalse(validator.isValid(eventDays, context));
    }
}
