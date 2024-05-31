package greencity.service;

import greencity.dto.event.*;
import greencity.dto.event.model.EventDayInfo;
import greencity.dto.event.model.EventImage;
import greencity.dto.event.model.EventModelDto;
import greencity.dto.user.UserVO;
//import greencity.repository.EventRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableCaching
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    //private final EventRepo eventRepo;
    private final ModelMapper modelMapper;

    @Override
    public EventModelDto save(EventRequestSaveDto event, List<MultipartFile> images, UserVO author) {
        List<EventDayInfo> dayInfos = new ArrayList<>();
        for (EventSaveDayInfoDto dayInfoDto: event.getDaysInfo()) {
            dayInfos.add(modelMapper.map(dayInfoDto, EventDayInfo.class));
        }

        List<EventImage> eventImages = new ArrayList<>();
        String[] uploadedImages = uploadImages(images.toArray(new MultipartFile[0]));

        eventImages = modelMapper.map(uploadedImages, eventImages.getClass());

        EventModelDto eventModelDto = EventModelDto.builder()
                .title(event.getTitle())
                .dayInfos(dayInfos)
                .isOpen(event.isOpen())
                .description(event.getDescription())
                .images(eventImages)
                .author(author)
                .build();
        return null;//eventRepo.save(eventModelDto);
    }

    @Override
    public void delete(Long id, UserVO author) {}

    @Override
    public EventModelDto update(EventRequestSaveDto event, List<MultipartFile> images, UserVO author) {
        return null;
    }

    @Override
    public List<EventModelDto> findAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public EventModelDto findById(Long id) {
        return null;
    }

    @Override
    public String uploadImage(MultipartFile image) {
        return "";
    }

    @Override
    public String[] uploadImages(MultipartFile[] images) {
        return new String[0];
    }

    @Override
    public List<EventModelDto> findAllByAuthor(Pageable pageable, Long userId) {
        return List.of();
    }
}
