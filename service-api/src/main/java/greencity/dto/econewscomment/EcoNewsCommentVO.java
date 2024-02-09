package greencity.dto.econewscomment;

import greencity.dto.econews.EcoNewsVO;
import greencity.dto.user.UserVO;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EcoNewsCommentVO {
    private Long id;

    @Size(min = 1, max = 8000)
    private String text;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    private EcoNewsCommentVO parentComment;

    private List<EcoNewsCommentVO> comments = new ArrayList<>();

    private UserVO user;

    private EcoNewsVO ecoNews;

    private boolean deleted;

    private boolean currentUserLiked = false;

    private Set<UserVO> usersLiked = new HashSet<>();
}
