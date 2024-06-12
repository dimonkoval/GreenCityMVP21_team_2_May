package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.econewscomment.EcoNewsCommentVO;
import greencity.entity.EcoNewsComment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class EcoNewsCommentVOMapperTest {

    @InjectMocks
    private EcoNewsCommentVOMapper mapper;

    @Test
    void convert_WithoutParentComment() {
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
        ecoNewsComment.setEcoNews(ModelUtils.getEcoNews());
        ecoNewsComment.setParentComment(null);

        EcoNewsCommentVO result = mapper.convert(ecoNewsComment);

        assertEquals(ecoNewsComment.getId(), result.getId());
        assertEquals(ecoNewsComment.getModifiedDate(), result.getModifiedDate());
        assertEquals(ecoNewsComment.getUser().getId(), result.getUser().getId());
        assertEquals(ecoNewsComment.getUser().getName(), result.getUser().getName());
        assertEquals(ecoNewsComment.getText(), result.getText());
        assertEquals(ecoNewsComment.getUsersLiked().size(), result.getUsersLiked().size());
        assertEquals(ecoNewsComment.isCurrentUserLiked(), result.isCurrentUserLiked());
        assertEquals(ecoNewsComment.getCreatedDate(), result.getCreatedDate());
        assertEquals(ecoNewsComment.getEcoNews().getId(), result.getEcoNews().getId());
        assertNull(result.getParentComment());
    }

    @Test
    void convert_WithParentComment() {
        LocalDateTime dateTime = LocalDateTime.now();
        EcoNewsComment parentComment = ModelUtils.getEcoNewsComment();
        EcoNewsComment ecoNewsComment = ModelUtils.getEcoNewsComment();

        parentComment.setId(2L);
        parentComment.setModifiedDate(dateTime);
        parentComment.setDeleted(false);
        parentComment.setCreatedDate(dateTime);
        parentComment.setText("Parent text");
        parentComment.setUser(ModelUtils.getUser());
        parentComment.setUsersLiked(Collections.emptySet());
        parentComment.setCurrentUserLiked(false);
        parentComment.setEcoNews(ModelUtils.getEcoNews());
        parentComment.setParentComment(null);

        ecoNewsComment.setId(1L);
        ecoNewsComment.setModifiedDate(dateTime);
        ecoNewsComment.setDeleted(false);
        ecoNewsComment.setCreatedDate(dateTime);
        ecoNewsComment.setText("Sample text");
        ecoNewsComment.setUser(ModelUtils.getUser());
        ecoNewsComment.setUsersLiked(Collections.emptySet());
        ecoNewsComment.setCurrentUserLiked(false);
        ecoNewsComment.setEcoNews(ModelUtils.getEcoNews());
        ecoNewsComment.setParentComment(parentComment);

        EcoNewsCommentVO result = mapper.convert(ecoNewsComment);

        assertEquals(ecoNewsComment.getId(), result.getId());
        assertEquals(ecoNewsComment.getModifiedDate(), result.getModifiedDate());
        assertEquals(ecoNewsComment.getUser().getId(), result.getUser().getId());
        assertEquals(ecoNewsComment.getUser().getName(), result.getUser().getName());
        assertEquals(ecoNewsComment.getText(), result.getText());
        assertEquals(ecoNewsComment.getUsersLiked().size(), result.getUsersLiked().size());
        assertEquals(ecoNewsComment.isCurrentUserLiked(), result.isCurrentUserLiked());
        assertEquals(ecoNewsComment.getCreatedDate(), result.getCreatedDate());
        assertEquals(ecoNewsComment.getEcoNews().getId(), result.getEcoNews().getId());

        EcoNewsCommentVO parentResult = result.getParentComment();
        assertEquals(parentComment.getId(), parentResult.getId());
        assertEquals(parentComment.getModifiedDate(), parentResult.getModifiedDate());
        assertEquals(parentComment.getUser().getId(), parentResult.getUser().getId());
        assertEquals(parentComment.getUser().getName(), parentResult.getUser().getName());
        assertEquals(parentComment.getText(), parentResult.getText());
        assertEquals(parentComment.getUsersLiked().size(), parentResult.getUsersLiked().size());
        assertEquals(parentComment.isCurrentUserLiked(), parentResult.isCurrentUserLiked());
        assertEquals(parentComment.getCreatedDate(), parentResult.getCreatedDate());
        assertEquals(parentComment.getEcoNews().getId(), parentResult.getEcoNews().getId());
        assertNull(parentResult.getParentComment());
    }
}
