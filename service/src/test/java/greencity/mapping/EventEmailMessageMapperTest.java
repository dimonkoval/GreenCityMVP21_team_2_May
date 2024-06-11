package greencity.mapping;

import greencity.ModelUtils;
import greencity.entity.event.EventAddress;
import greencity.entity.event.EventDayInfo;
import greencity.entity.event.Event;
import greencity.dto.user.UserVO;
import greencity.message.EventEmailMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class EventEmailMessageMapperTest {

    @InjectMocks
    private EventEmailMessageMapper eventEmailMessageMapper;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(eventEmailMessageMapper, "greenCityMvpServerAddress", "http://localhost:8080");
    }

    @Test
    void convert() {
        UserVO author = ModelUtils.getUserVO();
        EventAddress address = ModelUtils.getEventAddress();
        EventDayInfo eventDayInfo = EventDayInfo.builder()
                .id(1L)
                .isAllDay(false)
                .startDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 11, 24, 12, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 11, 24, 14, 0), ZoneId.systemDefault()))
                .dayNumber(1)
                .link("some link")
                .address(address)
                .build();

        List<EventDayInfo> dayInfos = new LinkedList<>();
        dayInfos.add(eventDayInfo);

        Event event = Event.builder()
                .id(1L)
                .title("Title")
                .dayInfos(dayInfos)
                .description("Description")
                .isOpen(true)
                .images(new ArrayList<>())
                .author(author)
                .build();

        EventEmailMessage expected = ModelUtils.getEventEmailMessage();

        EventEmailMessage emailMessage = eventEmailMessageMapper.convert(event);

        assertEquals(expected.getEmail(), emailMessage.getEmail());
        assertEquals(expected.getSubject(), emailMessage.getSubject());
        assertEquals(expected.getAuthor(), emailMessage.getAuthor());
        assertEquals(expected.getEventTitle(), emailMessage.getEventTitle());
        assertEquals(expected.getDescription(), emailMessage.getDescription());
        assertEquals(expected.isOpen(), emailMessage.isOpen());
        assertEquals(expected.getStatus(), emailMessage.getStatus());
        assertEquals(expected.getLink(), emailMessage.getLink());
        assertEquals(expected.getStartDateTime(), emailMessage.getStartDateTime());
        assertEquals(expected.getEndDateTime(), emailMessage.getEndDateTime());
        assertEquals(expected.getAddress(), emailMessage.getAddress());
        assertEquals(expected.getLinkToEvent(), emailMessage.getLinkToEvent());

    }

}
