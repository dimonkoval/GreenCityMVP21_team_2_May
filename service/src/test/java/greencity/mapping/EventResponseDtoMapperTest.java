package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.event.EventAuthorDto;
import greencity.dto.event.EventImageDto;
import greencity.dto.event.EventResponseDayInfoDto;
import greencity.dto.event.EventResponseDto;
import greencity.dto.event.model.EventDayInfo;
import greencity.dto.event.model.EventImage;
import greencity.dto.event.model.EventModelDto;
import greencity.dto.user.UserVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EventResponseDtoMapperTest {

    @InjectMocks
    private EventResponseDtoMapper mapper;

    @Mock
    private EventResponseDayInfoDtoMapper eventResponseDayInfoDtoMapper;

    @Mock
    private EventImageDtoMapper eventImageDtoMapperMapper;

    @Mock
    private EventAuthorDtoMapper eventAuthorDtoMapper;

    @Test
    void convert() {
        EventModelDto eventModelDto = ModelUtils.getEventModelDto();

        when(eventResponseDayInfoDtoMapper.convert(Mockito.any(EventDayInfo.class)))
                .thenReturn(new EventResponseDayInfoDto());
        when(eventImageDtoMapperMapper.convert(Mockito.any(EventImage.class)))
                .thenReturn(new EventImageDto());
        when(eventAuthorDtoMapper.convert(Mockito.any(UserVO.class)))
                .thenReturn(new EventAuthorDto());

        EventResponseDto eventResponseDto = mapper.convert(eventModelDto);

        assertEquals(eventModelDto.getId(), eventResponseDto.getId());
        assertEquals(eventModelDto.getTitle(), eventResponseDto.getTitle());
        assertEquals(eventModelDto.getDescription(), eventResponseDto.getDescription());
        assertEquals(eventModelDto.isOpen(), eventResponseDto.isOpen());
        assertEquals(eventModelDto.getDayInfos().size(), eventResponseDto.getDateTimes().size());
        assertEquals(eventModelDto.getImages().size(), eventResponseDto.getImages().size());

    }

    @Test
    void testMapAllToList() {
        List<EventModelDto> dtoList = Collections.singletonList(ModelUtils.getEventModelDto());

        when(eventResponseDayInfoDtoMapper.convert(Mockito.any(EventDayInfo.class)))
                .thenReturn(new EventResponseDayInfoDto());
        when(eventImageDtoMapperMapper.convert(Mockito.any(EventImage.class)))
                .thenReturn(new EventImageDto());
        when(eventAuthorDtoMapper.convert(Mockito.any(UserVO.class)))
                .thenReturn(new EventAuthorDto());

        List<EventResponseDto> eventResponseDtoList = mapper.mapAllToList(dtoList);

        assertEquals(dtoList.size(), eventResponseDtoList.size());
    }

}
