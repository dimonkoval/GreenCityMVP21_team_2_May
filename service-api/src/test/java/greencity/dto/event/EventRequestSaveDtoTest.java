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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EventRequestSaveDtoTest {

    private static EventRequestSaveDto eventRequestSaveDto;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeEach
    void setUp() {
        eventRequestSaveDto = new EventRequestSaveDto();
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
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
    @MethodSource("provideValidDaysInfo")
    void validDaysInfo(List<EventSaveDayInfoDto> daysInfo) {
        eventRequestSaveDto.setTitle("Valid Title");
        eventRequestSaveDto.setDescription("description1234567890");
        eventRequestSaveDto.setTags(List.of("Social"));
        eventRequestSaveDto.setDaysInfo(daysInfo);

        Set<ConstraintViolation<EventRequestSaveDto>> violations = validator.validate(eventRequestSaveDto);
        assertEquals(0, violations.size());
    }

    private static Stream<Arguments> provideValidDaysInfo() {
        return Stream.of(
                Arguments.of(List.of(createValidEventSaveDayInfoDto()))
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
                Arguments.of(createInvalidEventSaveDayInfoDto("UniqueEventDates"), "You cannot use the same date for multiple days")
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
