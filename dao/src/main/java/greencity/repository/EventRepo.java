package greencity.repository;

import greencity.dto.event.*;
import greencity.dto.user.UserVO;
import greencity.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepo {

    private List<EventModelDto> events = new ArrayList<>();
    private ModelMapper modelMapper = new ModelMapper();
    private AtomicLong eventId = new AtomicLong();

    public EventModelDto save(EventSaveDto event, List<MultipartFile> images, UserVO author) {
        List<EventDayInfo> dayInfos = new ArrayList<>();
        for (EventSaveDayInfoDto dayInfoDto: event.getDaysInfo()) {
            dayInfos.add(modelMapper.map(dayInfoDto, EventDayInfo.class));
        }
        EventModelDto eventModelDto = EventModelDto.builder()
                .id(eventId.incrementAndGet())
                .title(event.getTitle())
                .dayInfos(dayInfos)
                .isOpen(event.isOpen())
                .description(event.getDescription())
                //.eventImages(             )
                .author(modelMapper.map(author, User.class))
                .build();

        events.add(eventModelDto);

        return eventModelDto;
    }



}
