package greencity.service;

import greencity.dto.event.EventSaveDto;
import greencity.dto.event.EventModelDto;
import greencity.dto.user.UserVO;
import greencity.repository.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@EnableCaching
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final EventRepo eventRepo;

    @Override
    public EventModelDto save(EventSaveDto event, List<MultipartFile> images, UserVO author) {
        return eventRepo.save(event, images, author);
    }

    @Override
    public void delete(Long id, UserVO author) {}

    @Override
    public EventModelDto update(EventSaveDto event, List<MultipartFile> images, UserVO author) {
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
