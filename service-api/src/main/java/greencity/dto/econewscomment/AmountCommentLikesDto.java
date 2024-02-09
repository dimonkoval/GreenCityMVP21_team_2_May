package greencity.dto.econewscomment;

import lombok.*;

import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AmountCommentLikesDto {
    @NotEmpty
    private Long id;

    private Integer amountLikes;

    private Long userId;

    private boolean isLiked;
}
