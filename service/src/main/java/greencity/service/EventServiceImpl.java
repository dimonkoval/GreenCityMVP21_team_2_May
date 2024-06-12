package greencity.service;

import greencity.client.RestClient;
import greencity.constant.ErrorMessage;
import greencity.dto.PageableAdvancedDto;
import greencity.dto.event.*;
import greencity.entity.Tag;
import greencity.entity.event.EventImage;
import greencity.entity.event.Event;
import greencity.dto.tag.TagVO;
import greencity.dto.user.UserVO;
import greencity.entity.User;
import greencity.enums.TagType;
import greencity.exception.exceptions.NotSavedException;
import greencity.exception.exceptions.WrongIdException;
import greencity.message.EventEmailMessage;
import greencity.repository.EventRepo;
import greencity.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
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
    private final TagsService tagsService;
    private final ThreadPoolExecutor emailThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);


    @Override
    public EventResponseDto save(EventRequestSaveDto event, MultipartFile[] images, UserVO author) {
        String[] uploadedImages = uploadImages(images);

        List<EventImage> eventImages = new ArrayList<>();
        if (event.getMainImageNumber() == 0) {
            event.setMainImageNumber(1);
        }
        if (images != null && images.length > 0) {
            for (int i = 0; i < uploadedImages.length; i++) {
                eventImages.add(EventImage.builder().imagePath(uploadedImages[i]).isMain(false).build());

            }
            eventImages.get(event.getMainImageNumber() - 1).setMain(true);
        }

        Event eventToSave = modelMapper.map(event, Event.class);
        eventToSave.setImages(eventImages);
        User user = modelMapper.map(author, User.class);
        eventToSave.setAuthor(user);


        if (event.getTags() != null && event.getTags().size() > 0) {
            if (new HashSet<>(event.getTags()).size() < event.getTags().size()) {
                throw new NotSavedException(ErrorMessage.EVENT_NOT_SAVED);
            }
            List<TagVO> tagVOS = tagsService.findTagsByNamesAndType(
                    event.getTags(), TagType.EVENT);
            eventToSave.setTags(modelMapper.map(tagVOS,
                    new TypeToken<List<Tag>>() {
                    }.getType()));
        }

        eventToSave = eventRepo.save(eventToSave);

        EventEmailMessage eventEmailMessage = modelMapper.map(eventToSave, EventEmailMessage.class);
        sendEmailNotification(eventEmailMessage);

        return modelMapper.map(eventToSave, EventResponseDto.class);
    }

    @Override
    public void delete(Long id, UserVO author) {}

    @Override
    public EventResponseDto update(EventRequestSaveDto event, List<MultipartFile> images, UserVO author) {
        return null;
    }

    @Override
    public PageableAdvancedDto<EventResponseDto> findAll(Pageable pageable) {
        Page<Event> events = eventRepo.findAll(pageable);
        return buildPageableAdvancedDto(events);
    }

    @Override
    public EventResponseDto findById(Long id) {
        Event event = eventRepo
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
    public PageableAdvancedDto<EventResponseDto> findAllByAuthor(Pageable pageable, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new WrongIdException(ErrorMessage.USER_NOT_FOUND_BY_ID + userId));
        Page<Event> events = eventRepo.findAllByAuthorId(pageable, userId);
        return buildPageableAdvancedDto(events);
    }


    public void sendEmailNotification(EventEmailMessage eventEmailMessage) {
        RequestAttributes originalRequestAttributes = RequestContextHolder.getRequestAttributes();
        emailThreadPool.submit(() -> {
            try {
                RequestContextHolder.setRequestAttributes(originalRequestAttributes);
                restClient.sendEmailNotification(eventEmailMessage);
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        });
    }

    private PageableAdvancedDto<EventResponseDto> buildPageableAdvancedDto(Page<Event> eventPage) {
        List<EventResponseDto> eventResponseDtos = eventPage.stream()
                .map(event -> modelMapper.map(event, EventResponseDto.class))
                .toList();

        return new PageableAdvancedDto<>(
                eventResponseDtos,
                eventPage.getTotalElements(),
                eventPage.getPageable().getPageNumber(),
                eventPage.getTotalPages(),
                eventPage.getNumber(),
                eventPage.hasPrevious(),
                eventPage.hasNext(),
                eventPage.isFirst(),
                eventPage.isLast());
    }
}
