package greencity.dto.event;
import greencity.dto.event.model.EventStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventRequestSaveDtoTest {

    private EventRequestSaveDto eventRequestSaveDto;
    private ValidatorFactory validatorFactory;
    private Validator validator;

    @BeforeEach
    void setUp() {
        eventRequestSaveDto = new EventRequestSaveDto();
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @ParameterizedTest
    @MethodSource("provideValidAddress")
    void validAddress(EventAddressDto addressDto) {
        eventRequestSaveDto.setTitle("title");
        eventRequestSaveDto.setDescription("description1234567890");
        eventRequestSaveDto.setTags(List.of("Social"));
        EventSaveDayInfoDto eventDayInfoDto = EventSaveDayInfoDto.builder()
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .status(EventStatus.OFFLINE)
                .dayNumber(1)
                .address(addressDto)
                .build();
        eventRequestSaveDto.setDaysInfo(List.of(eventDayInfoDto));
        Set<ConstraintViolation<EventRequestSaveDto>> violations = validator.validate(eventRequestSaveDto);
        assertEquals(0, violations.size());
    }

    private static Stream<Arguments> provideValidAddress() {
        return Stream.of(
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(90))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(-90))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(180))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(-180))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn(null)
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("valid address")
                        .addressUa(null)
                        .build())
        );
    }


    @ParameterizedTest
    @MethodSource("provideInvalidAddress")
    void invalidAddress(EventAddressDto addressDto) {
        eventRequestSaveDto.setTitle("title");
        eventRequestSaveDto.setDescription("description1234567890");
        eventRequestSaveDto.setTags(List.of("Social"));
        EventSaveDayInfoDto eventDayInfoDto = EventSaveDayInfoDto.builder()
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .status(EventStatus.OFFLINE)
                .dayNumber(1)
                .address(addressDto)
                .build();
        eventRequestSaveDto.setDaysInfo(List.of(eventDayInfoDto));
        Set<ConstraintViolation<EventRequestSaveDto>> violations = validator.validate(eventRequestSaveDto);
        assertEquals(1, violations.size());
        assertEquals(eventRequestSaveDto.getDaysInfo(), violations.iterator().next().getInvalidValue());
        assertEquals("Please add address to the event.", violations.iterator().next().getMessage());
    }

    private static Stream<Arguments> provideInvalidAddress() {
        EventAddressDto nullable = null;
        return Stream.of(
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(-95))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(100))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(100))
                        .longitude(BigDecimal.valueOf(190))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(-200))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(-180))
                        .addressEn(null)
                        .addressUa(null)
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(null)
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn(null)
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(null)
                        .addressEn("valid address")
                        .addressUa(null)
                        .build()),
                Arguments.of(nullable)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidLink")
    void validLink(String link) {
        eventRequestSaveDto.setTitle("title");
        eventRequestSaveDto.setDescription("description1234567890");
        eventRequestSaveDto.setTags(List.of("Social"));
        EventSaveDayInfoDto eventDayInfoDto = EventSaveDayInfoDto.builder()
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .status(EventStatus.ONLINE)
                .dayNumber(1)
                .link(link)
                .build();
        eventRequestSaveDto.setDaysInfo(List.of(eventDayInfoDto));
        Set<ConstraintViolation<EventRequestSaveDto>> violations = validator.validate(eventRequestSaveDto);
        assertEquals(0, violations.size());
    }

    private static Stream<Arguments> provideValidLink() {
        return Stream.of(
                Arguments.of("https://"),
                Arguments.of("http://"),
                Arguments.of("https://google.com"),
                Arguments.of("http://site.com"),
                Arguments.of("https://any-other-sitenam3.org")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidLink")
    void invalidLink(String link) {
        eventRequestSaveDto.setTitle("title");
        eventRequestSaveDto.setDescription("description1234567890");
        eventRequestSaveDto.setTags(List.of("Social"));
        EventSaveDayInfoDto eventDayInfoDto = EventSaveDayInfoDto.builder()
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .status(EventStatus.ONLINE)
                .dayNumber(1)
                .link(link)
                .build();
        eventRequestSaveDto.setDaysInfo(List.of(eventDayInfoDto));
        Set<ConstraintViolation<EventRequestSaveDto>> violations = validator.validate(eventRequestSaveDto);
        assertEquals(1, violations.size());
        assertEquals(eventRequestSaveDto.getDaysInfo(), violations.iterator().next().getInvalidValue());
        assertEquals("Please add a link to the event. The link must start with http(s)://", violations.iterator().next().getMessage());
    }

    private static Stream<Arguments> provideInvalidLink() {
        String nullable = null;
        return Stream.of(
                Arguments.of("htps://"),
                Arguments.of("  "),
                Arguments.of("google.com"),
                Arguments.of(nullable),
                Arguments.of("www.any-site.org"),
                Arguments.of("invalid")
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidLinkAndAddress")
    void validLinkAndAddress(String link, EventAddressDto addressDto) {
        eventRequestSaveDto.setTitle("title");
        eventRequestSaveDto.setDescription("description1234567890");
        eventRequestSaveDto.setTags(List.of("Social"));
        EventSaveDayInfoDto eventDayInfoDto = EventSaveDayInfoDto.builder()
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .status(EventStatus.ONLINE_OFFLINE)
                .dayNumber(1)
                .link(link)
                .address(addressDto)
                .build();
        eventRequestSaveDto.setDaysInfo(List.of(eventDayInfoDto));
        Set<ConstraintViolation<EventRequestSaveDto>> violations = validator.validate(eventRequestSaveDto);
        assertEquals(0, violations.size());
    }

    private static Stream<Arguments> provideValidLinkAndAddress() {
        String nullable = null;
        return Stream.of(
                Arguments.of("https://", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of("http://", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(90))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of("https://google.com", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(-90))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of("http://site.com", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(-180))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of("https://any-other-sitenam3.org", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(180))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build())
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidLinkAndAddress")
    void invalidLinkAndAddress(String link, EventAddressDto addressDto) {
        eventRequestSaveDto.setTitle("title");
        eventRequestSaveDto.setDescription("description1234567890");
        eventRequestSaveDto.setTags(List.of("Social"));
        EventSaveDayInfoDto eventDayInfoDto = EventSaveDayInfoDto.builder()
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDate.now().plusYears(1),
                        LocalTime.of(0, 0), ZoneId.systemDefault()))
                .status(EventStatus.ONLINE_OFFLINE)
                .dayNumber(1)
                .link(link)
                .address(addressDto)
                .build();
        eventRequestSaveDto.setDaysInfo(List.of(eventDayInfoDto));
        Set<ConstraintViolation<EventRequestSaveDto>> violations = validator.validate(eventRequestSaveDto);
        assertEquals(2, violations.size());
        Iterator<ConstraintViolation<EventRequestSaveDto>> iterator = violations.iterator();
        ConstraintViolation<EventRequestSaveDto> violation = iterator.next();
        List<String> messages = new ArrayList<>();
        messages.add(violation.getMessage());
        assertEquals(eventRequestSaveDto.getDaysInfo(), violation.getInvalidValue());
        violation = iterator.next();
        messages.add(violation.getMessage());
        assertEquals(eventRequestSaveDto.getDaysInfo(), violation.getInvalidValue());
        assertTrue(messages.contains("Please add address to the event."));
        assertTrue(messages.contains("Please add a link to the event. The link must start with http(s)://"));
    }

    private static Stream<Arguments> provideInvalidLinkAndAddress() {
        String nullableLink = null;
        EventAddressDto nullableAddress = null;
        return Stream.of(
                Arguments.of("htps://", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(-95))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of("  ", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(100))
                        .longitude(BigDecimal.valueOf(190))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of("google.com", nullableAddress),
                Arguments.of(nullableLink, EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(-200))
                        .addressEn("valid address")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of("www.any-site.org", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(null)
                        .addressEn("valid address")
                        .addressUa(null)
                        .build()),
                Arguments.of("invalid", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(-180))
                        .addressEn(null)
                        .addressUa(null)
                        .build())
        );
    }
}
