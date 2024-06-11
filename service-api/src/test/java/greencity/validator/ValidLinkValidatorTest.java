package greencity.validator;

import greencity.dto.event.EventAddressDto;
import greencity.dto.event.EventSaveDayInfoDto;
import greencity.dto.event.model.EventStatus;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidLinkValidatorTest {
    @InjectMocks
    ValidLinkValidator validator;

    @Mock
    ConstraintValidatorContext context;

    @ParameterizedTest
    @MethodSource("provideValidData")
    void isValid_ReturnTrue(EventAddressDto addressDto, String link, EventStatus status) {
        EventSaveDayInfoDto dayInfoDto = EventSaveDayInfoDto.builder()
                .isAllDay(false)
                .startDateTime(ZonedDateTime.now())
                .endDateTime(ZonedDateTime.now().plusHours(2))
                .status(status)
                .address(addressDto)
                .link(link)
                .build();
        assertTrue(validator.isValid(List.of(dayInfoDto), context));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void isValid_ReturnFalse(EventAddressDto addressDto, String link, EventStatus status) {
        EventSaveDayInfoDto dayInfoDto = EventSaveDayInfoDto.builder()
                .isAllDay(false)
                .startDateTime(ZonedDateTime.now())
                .endDateTime(ZonedDateTime.now().plusHours(2))
                .status(status)
                .address(addressDto)
                .link(link)
                .build();
        assertFalse(validator.isValid(List.of(dayInfoDto), context));
    }

    private static Stream<Arguments> provideValidData() {
        return Stream.of(
                Arguments.of(EventAddressDto.builder()
                                .latitude(BigDecimal.valueOf(26.75))
                                .longitude(BigDecimal.valueOf(89.54))
                                .addressEn("valid address")
                                .addressUa("валідна адреса")
                                .build(),
                        "http://site.com",
                        EventStatus.ONLINE_OFFLINE),
                Arguments.of(EventAddressDto.builder()
                                .latitude(BigDecimal.valueOf(90))
                                .longitude(BigDecimal.valueOf(89.54))
                                .addressEn("valid address")
                                .addressUa("валідна адреса")
                                .build(),
                        null,
                        EventStatus.OFFLINE),
                Arguments.of(null,
                        "https://site.com",
                        EventStatus.ONLINE)
        );
    }

    private static Stream<Arguments> provideInvalidData() {
        return Stream.of(
                Arguments.of(null,
                        null,
                        EventStatus.ONLINE),
                Arguments.of(EventAddressDto.builder()
                                .latitude(BigDecimal.valueOf(26.75))
                                .longitude(BigDecimal.valueOf(89.54))
                                .addressEn("valid address")
                                .addressUa("валідна адреса")
                                .build(),
                        "   ",
                        EventStatus.ONLINE_OFFLINE),
                Arguments.of(null,
                        "link",
                        EventStatus.ONLINE),
                Arguments.of(EventAddressDto.builder()
                                .latitude(BigDecimal.valueOf(90))
                                .longitude(BigDecimal.valueOf(89.54))
                                .addressEn("valid address")
                                .addressUa("валідна адреса")
                                .build(),
                        "https://google.com",
                        EventStatus.OFFLINE)
        );
    }
}