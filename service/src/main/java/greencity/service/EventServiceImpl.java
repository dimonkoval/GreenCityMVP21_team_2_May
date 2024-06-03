package greencity.service;

import greencity.client.RestClient;
import greencity.constant.EmailNotificationMessagesConstants;
import greencity.constant.ErrorMessage;
import greencity.dto.event.*;
import greencity.dto.event.model.EventImage;
import greencity.dto.event.model.EventModelDto;
import greencity.dto.user.UserVO;
import greencity.entity.User;
import greencity.exception.exceptions.WrongIdException;
import greencity.message.EventEmailMessage;
import greencity.repository.EventRepo;
import greencity.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@EnableCaching
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final EventRepo eventRepo;
    private final ModelMapper modelMapper;
    private final FileService fileService;
    private final UserRepo userRepo;
    private final RestClient restClient;
    private final ThreadPoolExecutor emailThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    @Override
    public EventResponseDto save(EventRequestSaveDto event, MultipartFile[] images, UserVO author) {
        String[] uploadedImages = uploadImages(images);

        List<EventImage> eventImages = new ArrayList<>();
        if (event.getMainImageNumber() == 0) {
            event.setMainImageNumber(1);
        }
        if (images.length > 0) {
            for (int i = 0; i < uploadedImages.length; i++) {
                eventImages.add(EventImage.builder().imagePath(uploadedImages[i]).isMain(false).build());

            }
            eventImages.get(event.getMainImageNumber() - 1).setMain(true);
        }

        EventModelDto eventModelDto = modelMapper.map(event, EventModelDto.class);
        eventModelDto.setImages(eventImages);
        eventModelDto.setAuthor(author);
        eventModelDto = eventRepo.save(eventModelDto);

        sendEmailNotification(EventEmailMessage.builder()
                .email(author.getEmail())
                .subject(EmailNotificationMessagesConstants.EVENT_CREATION_SUBJECT)
                .author(author.getName())
                .message(String.format(
                        EmailNotificationMessagesConstants.EVENT_CREATION_MESSAGE, eventModelDto.getTitle()
                ))
                .description(eventModelDto.getDescription())
                .isOpen(eventModelDto.isOpen())
                .isOnline(eventModelDto.getDayInfos().getFirst().isOnline())
                .startDateTime(eventModelDto.getDayInfos().getFirst().getStartDateTime())
                .endDateTime(eventModelDto.getDayInfos().getFirst().getEndDateTime())
                .location(eventModelDto.getDayInfos().getFirst().getLocation().getLocation())
                .build());

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
        EventModelDto event = eventRepo
                .findById(id)
                .orElseThrow(() -> new WrongIdException(ErrorMessage.EVENT_NOT_FOUND_BY_ID + id));
        return modelMapper.map(event, EventResponseDto.class);
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
        if (images != null) {
            return Arrays.stream(images).map(fileService::upload).toArray(String[]::new);
        }
        return new String[0];
    }

    @Override
    public List<EventResponseDto> findAllByAuthor(Pageable pageable, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new WrongIdException(ErrorMessage.USER_NOT_FOUND_BY_ID + userId));
        List<EventModelDto> eventModelDtos = eventRepo.findAllByAuthorId(pageable, userId);
        return eventModelDtos.stream().map(e -> modelMapper.map(e, EventResponseDto.class)).toList();
    }


    public void sendEmailNotification(EventEmailMessage generalEmailMessage) {
        RequestAttributes originalRequestAttributes = RequestContextHolder.getRequestAttributes();
        emailThreadPool.submit(() -> {
            try {
                RequestContextHolder.setRequestAttributes(originalRequestAttributes);
                restClient.sendEmailNotification(generalEmailMessage);
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        });
    }
}
