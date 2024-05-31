package greencity.repository;

import greencity.dto.event.EventDto;
import greencity.dto.event.EventModelDto;
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

    public EventModelDto save(EventDto event, List<MultipartFile> images, UserVO author) {

        EventModelDto eventModelDto = EventModelDto.builder()
                .id(eventId.incrementAndGet())
                .title(event.getTitle())
                .eventDateTimes(event.getEventDateTimes())
                .isOnline(event.getIsOnline())
                .description(event.getDescription())
                .isEventOpen(event.isEventOpen())
                .eventLocations(event.getEventLocations())
                .webPages(event.setWebPages())
                .eventImages(

                )
                .author(modelMapper.map(author, User.class))
                .participants()
                .build();

        events.add(eventModelDto);

        return eventModelDto;
    }



}
