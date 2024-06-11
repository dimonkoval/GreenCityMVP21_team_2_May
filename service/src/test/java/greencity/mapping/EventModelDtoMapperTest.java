package greencity.mapping;

import greencity.dto.event.EventAddressDto;
import greencity.dto.event.EventRequestSaveDto;
import greencity.dto.event.EventSaveDayInfoDto;
import greencity.dto.event.model.EventModelDto;
import greencity.dto.event.model.EventStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static greencity.ModelUtils.getEventModelDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class EventModelDtoMapperTest {
    @InjectMocks
    EventModelDtoMapper mapper;

    EventModelDto expected = getEventModelDto();

    @Test
    void convert() {
        expected.setId(null);
        expected.setImages(null);
        expected.setAuthor(null);
        expected.getDayInfos().get(0).setId(null);

        EventSaveDayInfoDto dayInfoDto = new EventSaveDayInfoDto();
        dayInfoDto.setAllDay(expected.getDayInfos().get(0).isAllDay());
        dayInfoDto.setDayNumber(expected.getDayInfos().get(0).getDayNumber());
        dayInfoDto.setStartDateTime(expected.getDayInfos().get(0).getStartDateTime());
        dayInfoDto.setEndDateTime(expected.getDayInfos().get(0).getEndDateTime());
        dayInfoDto.setStatus(EventStatus.ONLINE);
        if (expected.getDayInfos().get(0).getAddress() == null) {
            dayInfoDto.setAddress(null);
        } else {
            dayInfoDto.setAddress(EventAddressDto.builder()
                    .latitude(expected.getDayInfos().get(0).getAddress().getLatitude())
                    .longitude(expected.getDayInfos().get(0).getAddress().getLongitude())
                    .addressEn(expected.getDayInfos().get(0).getAddress().getAddressEn())
                    .addressUa(expected.getDayInfos().get(0).getAddress().getAddressUa())
                    .build());
        }
        dayInfoDto.setLink(expected.getDayInfos().get(0).getLink());
        EventRequestSaveDto dtoToConvert = new EventRequestSaveDto();
        dtoToConvert.setTitle(expected.getTitle());
        dtoToConvert.setDaysInfo(List.of(dayInfoDto));
        dtoToConvert.setOpen(expected.isOpen());
        dtoToConvert.setDescription(expected.getDescription());

        assertEquals(expected, mapper.convert(dtoToConvert));
    }
}
