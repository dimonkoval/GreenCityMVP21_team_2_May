package greencity.dto.econewscomment;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AddEcoNewsCommentDtoRequest {
    @NotBlank(message = "The text of comment can not be empty")
    @Length(min = 1, max = 8000)
    private String text;

    private Long parentCommentId;
}
