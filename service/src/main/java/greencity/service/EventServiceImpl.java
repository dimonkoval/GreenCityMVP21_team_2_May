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
    public EventModelDto save(EventRequestSaveDto event, List<MultipartFile> images, int mainImageNumber, UserVO author) {
        String[] uploadedImages = uploadImages(images.toArray(new MultipartFile[0]));

        List<EventImage> eventImages = new ArrayList<>();
        eventImages =  modelMapper.map(uploadedImages, eventImages.getClass());
        if (mainImageNumber == 0) {
            eventImages.get(0).setMain(true);
        } else {
            eventImages.get(mainImageNumber).setMain(true);
        }

        EventModelDto eventModelDto = modelMapper.map(event, EventModelDto.class);
        eventModelDto.setImages(eventImages);
        eventModelDto.setAuthor(author);
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
