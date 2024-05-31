package greencity.service;

import greencity.dto.event.EventDto;
import greencity.dto.event.EventModelDto;
import greencity.dto.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@EnableCaching
@RequiredArgsConstructor
public class EventServiceEmpl implements EventService{

    @Override
    public EventModelDto save(EventDto event, List<MultipartFile> images, UserVO author) {
        return null;
    }

    @Override
    public void delete(Long id, UserVO author) {

    }

    @Override
    public EventModelDto update(EventDto event, List<MultipartFile> images, UserVO author) {
        return null;
    }

    @Override
    public List<EventModelDto> findAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public List<EventModelDto> findAllIncoming(Pageable pageable) {
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

    @Override
    public List<EventModelDto> findAllByParticipant(Pageable pageable, Long userId) {
        return List.of();
    }

    @Override
    public List<EventModelDto> findAllEventsBy(Pageable pageable, String query, String searchedField) {
        return List.of();
    }
}
