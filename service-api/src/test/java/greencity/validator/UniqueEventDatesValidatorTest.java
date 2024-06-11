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
class UniqueEventDatesValidatorTest {

    @InjectMocks
    private UniqueEventDatesValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void testValidUniqueEventDates() {
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
    void testInvalidUniqueEventDates() {
        List<EventSaveDayInfoDto> eventDays = Arrays.asList(
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.now().plusDays(1))
                        .endDateTime(ZonedDateTime.now().plusDays(1).plusHours(2))
                        .build(),
                EventSaveDayInfoDto.builder()
                        .startDateTime(ZonedDateTime.now().plusDays(1))
                        .endDateTime(ZonedDateTime.now().plusDays(1).plusHours(3))
                        .build()
        );

        assertFalse(validator.isValid(eventDays, context));
    }

}
