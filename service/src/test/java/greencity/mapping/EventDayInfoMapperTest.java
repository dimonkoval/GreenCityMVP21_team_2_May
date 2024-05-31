package greencity.mapping;

import greencity.dto.event.EventSaveDayInfoDto;
import greencity.dto.event.model.EventDayInfo;
import greencity.dto.event.model.EventLocationAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static greencity.ModelUtils.getEventDayInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class EventDayInfoMapperTest {
    @InjectMocks
    EventDayInfoMapper mapper;
    EventDayInfo expected = getEventDayInfo();

    @Test
    void convert() {
        expected.setId(null);
        expected.setEvent(null);
        EventSaveDayInfoDto dtoToConvert = new EventSaveDayInfoDto();
        dtoToConvert.setAllDay(expected.isAllDay());
        dtoToConvert.setOnline(expected.isOnline());
        dtoToConvert.setDayNumber(expected.getDayNumber());
        dtoToConvert.setStartTime(expected.getStartTime());
        dtoToConvert.setEndTime(expected.getEndTime());
        dtoToConvert.setDate(expected.getDate());
        dtoToConvert.setLocation(expected.getLocation().getLocation());

        assertEquals(expected, mapper.convert(dtoToConvert));
    }

    @Test
    void mapAllToList() {
        EventDayInfo expected1 = expected;
        expected1.setId(null);
        expected1.setEvent(null);
        EventDayInfo expected2 = EventDayInfo.builder()
                .isAllDay(true)
                .isOnline(false)
                .startTime(LocalTime.of(0,0))
                .endTime(LocalTime.of(23, 59))
                .dayNumber(2)
                .date(LocalDate.of(2025, 11, 26))
                .location(new EventLocationAddress("address"))
                .build();
        EventDayInfo expected3 = EventDayInfo.builder()
                .isAllDay(false)
                .isOnline(false)
                .startTime(LocalTime.of(13,0))
                .endTime(LocalTime.of(22, 0))
                .dayNumber(3)
                .date(LocalDate.of(2025, 11, 30))
                .location(new EventLocationAddress("address2"))
                .build();
        List<EventDayInfo> expectedList = new ArrayList<>();
        expectedList.add(expected1);
        expectedList.add(expected2);
        expectedList.add(expected3);

        EventSaveDayInfoDto toConvert1 = new EventSaveDayInfoDto();
        toConvert1.setAllDay(expected1.isAllDay());
        toConvert1.setOnline(expected1.isOnline());
        toConvert1.setStartTime(expected1.getStartTime());
        toConvert1.setEndTime(expected1.getEndTime());
        toConvert1.setDayNumber(expected1.getDayNumber());
        toConvert1.setDate(expected1.getDate());
        toConvert1.setLocation(expected1.getLocation().getLocation());
        EventSaveDayInfoDto toConvert2 = new EventSaveDayInfoDto();
        toConvert2.setAllDay(expected2.isAllDay());
        toConvert2.setOnline(expected2.isOnline());
        toConvert2.setStartTime(expected2.getStartTime());
        toConvert2.setEndTime(expected2.getEndTime());
        toConvert2.setDayNumber(expected2.getDayNumber());
        toConvert2.setDate(expected2.getDate());
        toConvert2.setLocation(expected2.getLocation().getLocation());
        EventSaveDayInfoDto toConvert3 = new EventSaveDayInfoDto();
        toConvert3.setAllDay(expected3.isAllDay());
        toConvert3.setOnline(expected3.isOnline());
        toConvert3.setStartTime(expected3.getStartTime());
        toConvert3.setEndTime(expected3.getEndTime());
        toConvert3.setDayNumber(expected3.getDayNumber());
        toConvert3.setDate(expected3.getDate());
        toConvert3.setLocation(expected3.getLocation().getLocation());
        List<EventSaveDayInfoDto> listToConvert = new ArrayList<>();
        listToConvert.add(toConvert1);
        listToConvert.add(toConvert2);
        listToConvert.add(toConvert3);

        List<EventDayInfo> result = mapper.mapAllToList(listToConvert);
        assertEquals(expectedList.size(), result.size());
        assertTrue(result.containsAll(expectedList));
    }
}
