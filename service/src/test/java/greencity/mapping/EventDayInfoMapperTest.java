package greencity.mapping;

import greencity.dto.event.EventAddressDto;
import greencity.dto.event.EventSaveDayInfoDto;
import greencity.dto.event.model.EventAddress;
import greencity.dto.event.model.EventDayInfo;
import greencity.dto.event.model.EventStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.*;
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
        dtoToConvert.setDayNumber(expected.getDayNumber());
        dtoToConvert.setStartDateTime(expected.getStartDateTime());
        dtoToConvert.setEndDateTime(expected.getEndDateTime());
        dtoToConvert.setStatus(expected.getStatus());
        dtoToConvert.setLink(expected.getLink());
        if (expected.getAddress() == null) {
            dtoToConvert.setAddress(null);
        } else {
            dtoToConvert.setAddress(EventAddressDto.builder()
                    .latitude(expected.getAddress().getLatitude())
                    .longitude(expected.getAddress().getLongitude())
                    .addressEn(expected.getAddress().getAddressEn())
                    .addressUa(expected.getAddress().getAddressUa())
                    .build());
        }

        assertEquals(expected, mapper.convert(dtoToConvert));
    }

    @Test
    void mapAllToList() {
        EventDayInfo expected1 = expected;
        expected1.setId(null);
        expected1.setEvent(null);
        EventDayInfo expected2 = EventDayInfo.builder()
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 11, 26, 0,0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 11, 26, 23, 59), ZoneId.systemDefault()))
                .dayNumber(2)
                .status(EventStatus.OFFLINE)
                .address(EventAddress.builder()
                        .latitude(BigDecimal.ONE)
                        .longitude(BigDecimal.TWO)
                        .addressEn("address")
                        .addressUa("адреса")
                        .build())
                .build();
        EventDayInfo expected3 = EventDayInfo.builder()
                .isAllDay(false)
                .startDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 11, 30, 13,0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 11, 30, 22, 0), ZoneId.systemDefault()))
                .dayNumber(3)
                .status(EventStatus.OFFLINE)
                .address(EventAddress.builder()
                        .latitude(BigDecimal.ONE)
                        .longitude(BigDecimal.TWO)
                        .addressEn("address2")
                        .addressUa("адреса2")
                        .build())
                .build();
        List<EventDayInfo> expectedList = new ArrayList<>();
        expectedList.add(expected1);
        expectedList.add(expected2);
        expectedList.add(expected3);

        EventSaveDayInfoDto toConvert1 = new EventSaveDayInfoDto();
        toConvert1.setAllDay(expected1.isAllDay());
        toConvert1.setStartDateTime(expected1.getStartDateTime());
        toConvert1.setEndDateTime(expected1.getEndDateTime());
        toConvert1.setDayNumber(expected1.getDayNumber());
        toConvert1.setStatus(expected1.getStatus());
        toConvert1.setLink(expected1.getLink());
        EventSaveDayInfoDto toConvert2 = new EventSaveDayInfoDto();
        toConvert2.setAllDay(expected2.isAllDay());
        toConvert2.setStartDateTime(expected2.getStartDateTime());
        toConvert2.setEndDateTime(expected2.getEndDateTime());
        toConvert2.setDayNumber(expected2.getDayNumber());
        toConvert2.setStatus(expected2.getStatus());
        toConvert2.setAddress(EventAddressDto.builder()
                .latitude(expected2.getAddress().getLatitude())
                .longitude(expected2.getAddress().getLongitude())
                .addressEn(expected2.getAddress().getAddressEn())
                .addressUa(expected2.getAddress().getAddressUa())
                .build());
        EventSaveDayInfoDto toConvert3 = new EventSaveDayInfoDto();
        toConvert3.setAllDay(expected3.isAllDay());
        toConvert3.setStartDateTime(expected3.getStartDateTime());
        toConvert3.setEndDateTime(expected3.getEndDateTime());
        toConvert3.setDayNumber(expected3.getDayNumber());
        toConvert3.setStatus(expected3.getStatus());
        toConvert3.setAddress(EventAddressDto.builder()
                .latitude(expected3.getAddress().getLatitude())
                .longitude(expected3.getAddress().getLongitude())
                .addressEn(expected3.getAddress().getAddressEn())
                .addressUa(expected3.getAddress().getAddressUa())
                .build());
        List<EventSaveDayInfoDto> listToConvert = new ArrayList<>();
        listToConvert.add(toConvert1);
        listToConvert.add(toConvert2);
        listToConvert.add(toConvert3);

        List<EventDayInfo> result = mapper.mapAllToList(listToConvert);
        assertEquals(expectedList.size(), result.size());
        assertTrue(result.containsAll(expectedList));
    }
}
