package greencity.service;

import greencity.dto.event.EventDto;
import greencity.dto.event.EventModelDto;
import greencity.dto.user.UserVO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventService {
    EventModelDto save(EventDto event, List<MultipartFile> images, UserVO author);
    void delete(Long id, UserVO author);
    EventModelDto update(EventDto event, List<MultipartFile> images, UserVO author);

    //Pageable implements sorted criteria
    List<EventModelDto> findAll(Pageable pageable);

    List<EventModelDto> findAllIncoming(Pageable pageable);

    EventModelDto findById(Long id);

    String uploadImage(MultipartFile image);

    //need consider if we need both below and above
    String[] uploadImages(MultipartFile[] images);

    List<EventModelDto> findAllByAuthor(Pageable pageable, Long userId);

    List<EventModelDto> findAllByParticipant(Pageable pageable, Long userId);

    //may be refactored to a group of methods(by date, online/offline, title/description etc
    List<EventModelDto> findAllEventsBy(Pageable pageable, String query, String searchedField);
}
