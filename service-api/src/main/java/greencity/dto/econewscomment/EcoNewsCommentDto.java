package greencity.dto.econewscomment;

import greencity.enums.CommentStatus;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class EcoNewsCommentDto {
    @NotNull
    @Min(1)
    private Long id;

    @NotEmpty
    private LocalDateTime modifiedDate;

    private EcoNewsCommentAuthorDto author;

    private String text;

    private int replies;

    private int likes;

    private boolean currentUserLiked;

    private CommentStatus status;
}
