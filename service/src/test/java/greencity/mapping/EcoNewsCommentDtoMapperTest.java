package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.econewscomment.EcoNewsCommentDto;
import greencity.entity.EcoNewsComment;
import greencity.enums.CommentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class EcoNewsCommentDtoMapperTest {

    @InjectMocks
    private EcoNewsCommentDtoMapper mapper;

    @Test
    void convert_OriginalComment() {
        LocalDateTime dateTime = LocalDateTime.now();
        EcoNewsComment ecoNewsComment = ModelUtils.getEcoNewsComment();

        ecoNewsComment.setId(1L);
        ecoNewsComment.setModifiedDate(dateTime);
        ecoNewsComment.setDeleted(false);
        ecoNewsComment.setCreatedDate(dateTime);
        ecoNewsComment.setText("Sample text");
        ecoNewsComment.setUser(ModelUtils.getUser());
        ecoNewsComment.setUsersLiked(Collections.emptySet());
        ecoNewsComment.setCurrentUserLiked(false);

        EcoNewsCommentDto result = mapper.convert(ecoNewsComment);

        assertEquals(ecoNewsComment.getId(), result.getId());
        assertEquals(ecoNewsComment.getModifiedDate(), result.getModifiedDate());
        assertEquals(ecoNewsComment.getUser().getId(), result.getAuthor().getId());
        assertEquals(ecoNewsComment.getUser().getName(), result.getAuthor().getName());
        assertEquals(ecoNewsComment.getText(), result.getText());
        assertEquals(ecoNewsComment.getUsersLiked().size(), result.getLikes());
        assertEquals(ecoNewsComment.isCurrentUserLiked(), result.isCurrentUserLiked());
        assertEquals(CommentStatus.ORIGINAL, result.getStatus());
    }

    @Test
    void convert_EditedComment() {
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime modifiedDate = createdDate.plusDays(1);
        EcoNewsComment ecoNewsComment = ModelUtils.getEcoNewsComment();

        ecoNewsComment.setId(1L);
        ecoNewsComment.setModifiedDate(modifiedDate);
        ecoNewsComment.setDeleted(false);
        ecoNewsComment.setCreatedDate(createdDate);
        ecoNewsComment.setText("Edited text");
        ecoNewsComment.setUser(ModelUtils.getUser());
        ecoNewsComment.setUsersLiked(Collections.emptySet());
        ecoNewsComment.setCurrentUserLiked(false);

        EcoNewsCommentDto result = mapper.convert(ecoNewsComment);

        assertEquals(ecoNewsComment.getId(), result.getId());
        assertEquals(ecoNewsComment.getModifiedDate(), result.getModifiedDate());
        assertEquals(ecoNewsComment.getUser().getId(), result.getAuthor().getId());
        assertEquals(ecoNewsComment.getUser().getName(), result.getAuthor().getName());
        assertEquals(ecoNewsComment.getText(), result.getText());
        assertEquals(ecoNewsComment.getUsersLiked().size(), result.getLikes());
        assertEquals(ecoNewsComment.isCurrentUserLiked(), result.isCurrentUserLiked());
        assertEquals(CommentStatus.EDITED, result.getStatus());
    }

}
