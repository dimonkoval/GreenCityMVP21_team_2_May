package greencity.service;

import greencity.dto.PageableAdvancedDto;
import greencity.dto.event.EventRequestSaveDto;
import greencity.dto.event.EventResponseDto;
import greencity.dto.user.UserVO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EventService {
    EventResponseDto save(EventRequestSaveDto event, MultipartFile[] images, UserVO author);
    void delete(Long id, UserVO author);
    EventResponseDto update(EventRequestSaveDto event, List<MultipartFile> images, UserVO author);

    //Pageable implements sorted criteria
    PageableAdvancedDto<EventResponseDto> findAll(Pageable pageable);

    EventResponseDto findById(Long id);

    String uploadImage(MultipartFile image);

    //need consider if we need both below and above
    String[] uploadImages(MultipartFile[] images);

    PageableAdvancedDto<EventResponseDto> findAllByAuthor(Pageable pageable, Long userId);

    /**
     * Add an attender to the Event by id.
     *
     * @param eventId - event id.
     * @param user - user.
     */
    void addAttender(Long eventId, UserVO user);
}
