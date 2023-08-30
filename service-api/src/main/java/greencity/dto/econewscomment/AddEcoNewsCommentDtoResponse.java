package greencity.dto.econewscomment;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddEcoNewsCommentDtoResponse {
    @NotNull
    @Min(1)
    private Long id;

    @NotEmpty
    private EcoNewsCommentAuthorDto author;

    @NotEmpty
    private String text;

    @NotEmpty
    private LocalDateTime modifiedDate;
}
