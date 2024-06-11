package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.event.EventAuthorDto;
import greencity.dto.event.EventImageDto;
import greencity.dto.event.EventResponseDayInfoDto;
import greencity.dto.event.EventResponseDto;
import greencity.entity.event.EventDayInfo;
import greencity.entity.event.EventImage;
import greencity.entity.event.Event;
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

    @Mock
    private UserVOMapper userVOMapper;

    @Test
    void convert() {
        Event event = ModelUtils.getEventModelDto();

        when(eventResponseDayInfoDtoMapper.convert(Mockito.any(EventDayInfo.class)))
                .thenReturn(new EventResponseDayInfoDto());
        when(eventImageDtoMapperMapper.convert(Mockito.any(EventImage.class)))
                .thenReturn(new EventImageDto());
        when(eventAuthorDtoMapper.convert(Mockito.any(UserVO.class)))
                .thenReturn(new EventAuthorDto());

        EventResponseDto eventResponseDto = mapper.convert(event);

        assertEquals(event.getId(), eventResponseDto.getId());
        assertEquals(event.getTitle(), eventResponseDto.getTitle());
        assertEquals(event.getDescription(), eventResponseDto.getDescription());
        assertEquals(event.isOpen(), eventResponseDto.isOpen());
        assertEquals(event.getDayInfos().size(), eventResponseDto.getDateTimes().size());
        assertEquals(event.getImages().size(), eventResponseDto.getImages().size());

    }

    @Test
    void testMapAllToList() {
        List<Event> dtoList = Collections.singletonList(ModelUtils.getEventModelDto());

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
