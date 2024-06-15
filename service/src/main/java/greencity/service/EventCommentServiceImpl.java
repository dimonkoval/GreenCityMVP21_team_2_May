package greencity.service;

import greencity.constant.ErrorMessage;
import greencity.dto.PageableDto;
import greencity.dto.eventcomment.EventCommentRequestDto;
import greencity.dto.eventcomment.EventCommentResponseDto;
import greencity.dto.user.UserVO;
import greencity.entity.EventComment;
import greencity.entity.User;
import greencity.entity.event.Event;
import greencity.exception.exceptions.NotFoundException;
import greencity.repository.EventCommentRepo;
import greencity.repository.EventRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventCommentServiceImpl implements EventCommentService {
    private final EventRepo eventRepo;
    private final EventCommentRepo eventCommentRepo;
    private ModelMapper modelMapper;

    /**
     * Method to save {@link EventComment}.
     *
     * @param eventId                   id of {@link Event} to
     *                                    which we save comment.
     * @param requestDto dto with
     *                                    {@link EventComment}
     *                                    text, parentCommentId.
     * @param user                      {@link User} that saves the comment.
     * @return {@link EventCommentResponseDto} instance.
     */
    @Override
    public EventCommentResponseDto save(Long eventId, EventCommentRequestDto requestDto, UserVO user) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.EVENT_NOT_FOUND_BY_ID + eventId));
        EventComment eventComment = modelMapper.map(requestDto, EventComment.class);
        eventComment.setEvent(event);
        eventComment.setUser(modelMapper.map(user, User.class));
        eventComment = eventCommentRepo.save(eventComment);
        return modelMapper.map(eventComment, EventCommentResponseDto.class);
    }

    /**
     * Method to count not deleted comments to certain
     * {@link Event}.
     *
     * @param eventId to specify {@link Event}
     * @return amount of comments
     */
    @Override
    public int countOfComments(Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.EVENT_NOT_FOUND_BY_ID + eventId));
        return eventCommentRepo.countByEvent(event);
    }

    /**
     * Method returns all comments to certain event specified by eventId.
     *
     * @param userVO    current {@link User}
     * @param eventId specifies {@link Event} to which we
     *                  search for comments
     * @return all comments to certain event specified by eventId.
     */
    @Override
    public PageableDto<EventCommentResponseDto> getAllEventComments(Pageable pageable, Long eventId, UserVO userVO) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.EVENT_NOT_FOUND_BY_ID + eventId));
        Page<EventComment> pages = eventCommentRepo.findAllByEvent(event);
        List<EventCommentResponseDto> eventComments = pages.stream()
                .map(eventComment -> modelMapper.map(eventComment, EventCommentResponseDto.class))
                .toList();
        return new PageableDto<>(
                eventComments,
                pages.getTotalElements(),
                pages.getPageable().getPageNumber(),
                pages.getTotalPages());
    }
}
