package greencity.dto.event;
import greencity.enums.EventStatus;
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

import static org.junit.jupiter.api.Assertions.*;

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
                        LocalTime.of(23, 59), ZoneId.systemDefault()))
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
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("  ")
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("valid address")
                        .addressUa("")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(-180))
                        .addressEn(null)
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
                        LocalTime.of(23, 59), ZoneId.systemDefault()))
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
                        .addressEn("invalid address")
                        .addressUa("не валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(100))
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn("invalid address")
                        .addressUa("не валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(100))
                        .longitude(BigDecimal.valueOf(190))
                        .addressEn("invalid address")
                        .addressUa("не валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(-200))
                        .addressEn("invalid address")
                        .addressUa("не валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(null)
                        .longitude(BigDecimal.valueOf(89.54))
                        .addressEn(null)
                        .addressUa("не валідна адреса")
                        .build()),
                Arguments.of(EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(null)
                        .addressEn("invalid address")
                        .addressUa(null)
                        .build()),
                Arguments.of(nullable)
          );
    }

    @ParameterizedTest
    @MethodSource("provideValidTitles")
    void validTitle(String title) {
        eventRequestSaveDto.setTitle(title);
        eventRequestSaveDto.setDescription("description");
        eventRequestSaveDto.setTags(List.of("Social"));
        eventRequestSaveDto.setDaysInfo(List.of(createValidEventSaveDayInfoDto()));

        Set<ConstraintViolation<EventRequestSaveDto>> violations = validator.validate(eventRequestSaveDto);
        assertEquals(1, violations.size());
    }

    private static Stream<Arguments> provideValidTitles() {
        return Stream.of(
                Arguments.of("Valid Title"),
                Arguments.of("T".repeat(70)),
                Arguments.of("T".repeat(1))
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidTitles")
    void invalidTitle(String title) {
        eventRequestSaveDto.setTitle(title);
        eventRequestSaveDto.setDescription("description1234567890");
        eventRequestSaveDto.setTags(List.of("Social"));
        eventRequestSaveDto.setDaysInfo(List.of(createValidEventSaveDayInfoDto()));

        Set<ConstraintViolation<EventRequestSaveDto>> violations = validator.validate(eventRequestSaveDto);
        assertEquals(1, violations.size());
    }

    private static Stream<Arguments> provideInvalidTitles() {
        return Stream.of(
                Arguments.of("", "Title cannot be blank"),
                Arguments.of("T".repeat(71), "Title must be a maximum of 70 characters"),
                Arguments.of(null, "Title cannot be blank")
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
                        LocalTime.of(23, 59), ZoneId.systemDefault()))
                .status(EventStatus.ONLINE)
                .dayNumber(1)
                .link(link)
                .build();
        eventRequestSaveDto.setDaysInfo(List.of(eventDayInfoDto));
        Set<ConstraintViolation<EventRequestSaveDto>> violations = validator.validate(eventRequestSaveDto);
        assertEquals(0, violations.size());
    }
  
    @ParameterizedTest
    @MethodSource("provideValidDaysInfo")
    void validDaysInfo(List<EventSaveDayInfoDto> daysInfo) {
        eventRequestSaveDto.setTitle("Valid Title");
        eventRequestSaveDto.setDescription("description1234567890");
        eventRequestSaveDto.setTags(List.of("Social"));
        eventRequestSaveDto.setDaysInfo(daysInfo);
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

    private static Stream<Arguments> provideValidDaysInfo() {
        return Stream.of(
                Arguments.of(List.of(createValidEventSaveDayInfoDto(),
                        createValidEventSaveDayInfoDto()
                                .setStartDateTime(ZonedDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(0, 0), ZoneId.systemDefault()))
                                .setEndDateTime(ZonedDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(3, 0), ZoneId.systemDefault()))
                ))
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
                        LocalTime.of(23, 59), ZoneId.systemDefault()))
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
                        LocalTime.of(23, 59), ZoneId.systemDefault()))
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
                        .addressEn("  ")
                        .addressUa("")
                        .build()),
                Arguments.of("http://site.com", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(-180))
                        .addressEn("valid address")
                        .addressUa("")
                        .build()),
                Arguments.of("https://any-other-sitenam3.org", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(180))
                        .addressEn(null)
                        .addressUa("валідна адреса")
                        .build()),
                Arguments.of("https://link.valid", EventAddressDto.builder()
                        .latitude(BigDecimal.valueOf(26.75))
                        .longitude(BigDecimal.valueOf(-180))
                        .addressEn(null)
                        .addressUa(null)
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
                        LocalTime.of(23, 59), ZoneId.systemDefault()))
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
                        .addressEn("invalid address")
                        .addressUa("не валідна адреса")
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
                        .build())
        );
      }

    @ParameterizedTest
    @MethodSource("provideInvalidDaysInfo")
    void invalidDaysInfo(List<EventSaveDayInfoDto> daysInfo, String expectedMessage) {
        eventRequestSaveDto.setTitle("Valid Title");
        eventRequestSaveDto.setDescription("description1234567890");
        eventRequestSaveDto.setTags(List.of("Social"));
        eventRequestSaveDto.setDaysInfo(daysInfo);

        Set<ConstraintViolation<EventRequestSaveDto>> violations = validator.validate(eventRequestSaveDto);

        assertEquals(expectedMessage, violations.stream().findFirst().get().getMessage());
    }

    private static Stream<Arguments> provideInvalidDaysInfo() {
        return Stream.of(
                Arguments.of(createInvalidEventSaveDayInfoDto("StartBeforeEndTime"), "End time cannot be before Start time"),
                Arguments.of(createInvalidEventSaveDayInfoDto("EventDateAfterOneHour"), "First Event should be at least one hour after now"),
                Arguments.of(createInvalidEventSaveDayInfoDto("ValidAllDayEvent"), "An all-day event should begin at 00:00 and conclude at 23:59"),
                Arguments.of(createInvalidEventSaveDayInfoDto("ValidSequenceEventDates"), "Each event date must follow the previous one"),
                Arguments.of(createInvalidEventSaveDayInfoDto("ValidSameDay"), "StartDate and EndDate must be the same day"),
                Arguments.of(createInvalidEventSaveDayInfoDto("UniqueEventDates"), "You can't enter the same date for two days")
                );
    }

    private static List<EventSaveDayInfoDto> createInvalidEventSaveDayInfoDto(String type) {
        switch (type) {
            case "StartBeforeEndTime":
                return List.of(
                        EventSaveDayInfoDto.builder()
                                .isAllDay(false)
                                .startDateTime(ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 0), ZoneId.systemDefault()))
                                .endDateTime(ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(11, 0), ZoneId.systemDefault()))
                                .status(EventStatus.ONLINE)
                                .dayNumber(1)
                                .link("https://valid-link.com")
                                .build()
                );
            case "EventDateAfterOneHour":
                return List.of(
                        EventSaveDayInfoDto.builder()
                                .isAllDay(false)
                                .startDateTime(ZonedDateTime.now().plusMinutes(30))
                                .endDateTime(ZonedDateTime.now().plusHours(2))
                                .status(EventStatus.ONLINE)
                                .dayNumber(1)
                                .link("https://valid-link.com")
                                .build()
                );
            case "ValidAllDayEvent":
                return List.of(
                        EventSaveDayInfoDto.builder()
                                .isAllDay(true)
                                .startDateTime(ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(1, 0), ZoneId.systemDefault()))
                                .endDateTime(ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(23, 59), ZoneId.systemDefault()))
                                .status(EventStatus.ONLINE)
                                .dayNumber(1)
                                .link("https://valid-link.com")
                                .build()
                );
            case "ValidSameDay":
                return List.of(
                        EventSaveDayInfoDto.builder()
                                .isAllDay(false)
                                .startDateTime(ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(1, 0), ZoneId.systemDefault()))
                                .endDateTime(ZonedDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(23, 59), ZoneId.systemDefault()))
                                .status(EventStatus.ONLINE)
                                .dayNumber(1)
                                .link("https://valid-link.com")
                                .build()
                );
            case "ValidSequenceEventDates":
                return List.of(
                        EventSaveDayInfoDto.builder()
                                .isAllDay(true)
                                .startDateTime(ZonedDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(0, 0), ZoneId.systemDefault()))
                                .endDateTime(ZonedDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(23, 59), ZoneId.systemDefault()))
                                .status(EventStatus.ONLINE)
                                .dayNumber(1)
                                .link("https://valid-link.com")
                                .build(),
                        EventSaveDayInfoDto.builder()
                                .isAllDay(true)
                                .startDateTime(ZonedDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(0, 0), ZoneId.systemDefault()))
                                .endDateTime(ZonedDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(23, 59), ZoneId.systemDefault()))
                                .status(EventStatus.ONLINE)
                                .dayNumber(2)
                                .link("https://valid-link.com")
                                .build()
                );
            case "UniqueEventDates":
                return List.of(
                        EventSaveDayInfoDto.builder()
                                .isAllDay(true)
                                .startDateTime(ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0), ZoneId.systemDefault()))
                                .endDateTime(ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(23, 59), ZoneId.systemDefault()))
                                .status(EventStatus.ONLINE)
                                .dayNumber(1)
                                .link("https://valid-link.com")
                                .build(),
                        EventSaveDayInfoDto.builder()
                                .isAllDay(true)
                                .startDateTime(ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0), ZoneId.systemDefault()))
                                .endDateTime(ZonedDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(23, 59), ZoneId.systemDefault()))
                                .status(EventStatus.ONLINE)
                                .dayNumber(2)
                                .link("https://valid-link.com")
                                .build()
                );
            default:
                return List.of(createValidEventSaveDayInfoDto());
        }
    }

    private static EventSaveDayInfoDto createValidEventSaveDayInfoDto() {
        return EventSaveDayInfoDto.builder()
                .isAllDay(false)
                .startDateTime(ZonedDateTime.now().plusHours(2))
                .endDateTime(ZonedDateTime.now().plusHours(3))
                .status(EventStatus.ONLINE)
                .dayNumber(1)
                .link("https://valid-link.com")
                .build();
    }
}
