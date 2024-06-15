package greencity.service;

import greencity.dto.PageableDto;
import greencity.dto.eventcomment.EventCommentRequestDto;
import greencity.dto.eventcomment.EventCommentResponseDto;
import greencity.dto.user.UserVO;
import org.springframework.data.domain.Pageable;

public interface EventCommentService {
    /**
     * Method to save {@link EventComment}.
     *
     * @param eventId                   id of {@link Event} to which we save
     *                                    comment.
     * @param requestDto dto with {@link EventComment} text,
     *                                    parentCommentId.
     * @param user                        {@link UserVO} that saves the comment.
     * @return {@link EventCommentResponseDto} instance.
     */
    EventCommentResponseDto save(Long eventId, EventCommentRequestDto requestDto, UserVO user);

    int countOfComments(Long ecoNewsId);

    PageableDto<EventCommentResponseDto> getAllEventComments(Pageable pageable, Long eventId, UserVO userVO);
}
