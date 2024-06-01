package greencity.service;

import greencity.dto.event.*;
import greencity.dto.event.model.EventImage;
import greencity.dto.event.model.EventModelDto;
import greencity.dto.user.UserVO;
import greencity.repository.EventRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@EnableCaching
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final EventRepo eventRepo;
    private final ModelMapper modelMapper;
    private final FileService fileService;

    @Override
    public EventResponseDto save(EventRequestSaveDto event, MultipartFile[] images, int mainImageNumber, UserVO author) {
        String[] uploadedImages = uploadImages(images);

        List<EventImage> eventImages = new ArrayList<>();
        eventImages =  modelMapper.map(uploadedImages, eventImages.getClass());
        if (mainImageNumber == 0) {
            eventImages.get(0).setMain(true);
        } else {
            eventImages.get(mainImageNumber - 1).setMain(true);
        }

        EventModelDto eventModelDto = modelMapper.map(event, EventModelDto.class);
        eventModelDto.setImages(eventImages);
        eventModelDto.setAuthor(author);
        eventModelDto = eventRepo.save(eventModelDto);
        return modelMapper.map(eventModelDto, EventResponseDto.class);
    }

    @Override
    public void delete(Long id, UserVO author) {}

    @Override
    public EventModelDto update(EventRequestSaveDto event, List<MultipartFile> images, UserVO author) {
        return null;
    }

    @Override
    public List<EventResponseDto> findAll(Pageable pageable) {
        List<EventModelDto> eventModelDtos = eventRepo.findAll(pageable);
        return eventModelDtos.stream().map(e -> modelMapper.map(e, EventResponseDto.class)).toList();
    }

    @Override
    public EventResponseDto findById(Long id) {
        return modelMapper.map(eventRepo.findById(id), EventResponseDto.class);
    }

    @Override
    public String uploadImage(MultipartFile image) {
        if (image != null) {
            return fileService.upload(image);
        }
        return null;
    }

    @Override
    public String[] uploadImages(MultipartFile[] images) {
        return Arrays.stream(images).map(fileService::upload).toArray(String[]::new);
    }

    @Override
    public List<EventModelDto> findAllByAuthor(Pageable pageable, Long userId) {
        return eventRepo.findAllByAuthorId(pageable, userId);
    }
}
