package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.event.EventResponseDayInfoDto;
import greencity.dto.event.model.EventDayInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class EventResponseDayInfoDtoMapperTest {

    @InjectMocks
    private EventResponseDayInfoDtoMapper mapper;

    @Test
    void convert() {
        EventDayInfo eventDayInfo = ModelUtils.getEventDayInfo();

        EventResponseDayInfoDto expected = EventResponseDayInfoDto.builder()
                .isAllDay(eventDayInfo.isAllDay())
                .isOnline(eventDayInfo.isOnline())
                .startTime(eventDayInfo.getStartTime())
                .endTime(eventDayInfo.getEndTime())
                .dayNumber(eventDayInfo.getDayNumber())
                .date(eventDayInfo.getDate())
                .location(eventDayInfo.getLocation().toString())
                .build();

        assertEquals(expected, mapper.convert(eventDayInfo));
    }

}
