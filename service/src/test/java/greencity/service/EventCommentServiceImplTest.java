package greencity.service;

import greencity.ModelUtils;
import greencity.client.RestClient;
import greencity.constant.ErrorMessage;
import greencity.dto.user.UserVO;
import greencity.entity.EventComment;
import greencity.entity.User;
import greencity.enums.CommentStatus;
import greencity.exception.exceptions.NotFoundException;
import greencity.exception.exceptions.UserHasNoPermissionToAccessException;
import greencity.repository.EventCommentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static greencity.ModelUtils.getEventComment;
import static greencity.ModelUtils.getUserVO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventCommentServiceImplTest {

    @Mock
    private EventCommentRepo eventCommentRepo;

    @Mock
    private UserService userService;

    @InjectMocks
    private EventCommentServiceImpl eventCommentService;

    @Test
    void testUpdateCommentSuccess() {
        Long commentId = 1L;
        String editedText = "Updated Comment";
        String email = "email@email.com";

        UserVO currentUser = getUserVO();
        when(userService.findByEmail(email)).thenReturn(currentUser);
        EventComment eventComment = getEventComment();

        when(eventCommentRepo.findByIdAndStatusNot(commentId, CommentStatus.DELETED)).thenReturn(Optional.ofNullable(eventComment));

        eventCommentService.update(commentId, editedText, email);

        assert eventComment != null;
        assertEquals(CommentStatus.EDITED, eventComment.getStatus());
    }

    @Test
    void updateCommentThatDoesntExistsThrowException() {
        Long commentId = 1L;
        String editedText = "Updated Comment";
        String email = "email@email.com";

        when(eventCommentRepo.findByIdAndStatusNot(commentId, CommentStatus.DELETED)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                        () -> eventCommentService.update(commentId, editedText, email));
        assertEquals(ErrorMessage.EVENT_COMMENT_NOT_FOUND_BY_ID + commentId, notFoundException.getMessage());
    }

    @Test
    void updateCommentThatDoesntBelongsToUserThrowException() {
        User user = ModelUtils.getUser();
        UserVO currentUser = getUserVO();
        user.setId(2L);

        Long commentId = 1L;
        EventComment eventComment = getEventComment();
        eventComment.setUser(user);
        String editedText = "Updated text";
        String email = "email@email.com";
        when(userService.findByEmail(email)).thenReturn(currentUser);

        when(eventCommentRepo.findByIdAndStatusNot(commentId, CommentStatus.DELETED)).thenReturn(Optional.of(eventComment));

        UserHasNoPermissionToAccessException userHasNoPermissionToAccessException =
                assertThrows(UserHasNoPermissionToAccessException.class,
                        () -> eventCommentService.update(commentId, editedText, email));
        assertEquals(ErrorMessage.USER_HAS_NO_PERMISSION, userHasNoPermissionToAccessException.getMessage());
    }
}
