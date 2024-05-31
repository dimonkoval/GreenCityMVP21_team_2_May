package greencity.mapping;

import greencity.dto.event.EventRequestSaveDto;
import greencity.dto.event.EventSaveDayInfoDto;
import greencity.dto.event.model.EventModelDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
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
        dayInfoDto.setOnline(expected.getDayInfos().get(0).isOnline());
        dayInfoDto.setDayNumber(expected.getDayInfos().get(0).getDayNumber());
        dayInfoDto.setStartTime(expected.getDayInfos().get(0).getStartTime());
        dayInfoDto.setEndTime(expected.getDayInfos().get(0).getEndTime());
        dayInfoDto.setDate(expected.getDayInfos().get(0).getDate());
        dayInfoDto.setLocation(expected.getDayInfos().get(0).getLocation().getLocation());
        EventRequestSaveDto dtoToConvert = new EventRequestSaveDto();
        dtoToConvert.setTitle(expected.getTitle());
        dtoToConvert.setDaysInfo(List.of(dayInfoDto));
        dtoToConvert.setOpen(expected.isOpen());
        dtoToConvert.setDescription(expected.getDescription());

        assertEquals(expected, mapper.convert(dtoToConvert));
    }
}
