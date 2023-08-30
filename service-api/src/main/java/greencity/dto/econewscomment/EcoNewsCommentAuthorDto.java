package greencity.dto.econewscomment;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class EcoNewsCommentAuthorDto {
    @NotEmpty
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String userProfilePicturePath;
}
