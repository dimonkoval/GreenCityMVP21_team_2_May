package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.event.EventAddressDto;
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
                .startDateTime(eventDayInfo.getStartDateTime())
                .endDateTime(eventDayInfo.getEndDateTime())
                .dayNumber(eventDayInfo.getDayNumber())
                .status(eventDayInfo.getStatus())
                .address(eventDayInfo.getAddress() == null ? null :
                        EventAddressDto.builder()
                        .latitude(eventDayInfo.getAddress().getLatitude())
                        .longitude(eventDayInfo.getAddress().getLongitude())
                        .addressEn(eventDayInfo.getAddress().getAddressEn())
                        .addressUa(eventDayInfo.getAddress().getAddressUa())
                        .build())
                .link(eventDayInfo.getLink())
                .build();

        assertEquals(expected, mapper.convert(eventDayInfo));
    }

}
