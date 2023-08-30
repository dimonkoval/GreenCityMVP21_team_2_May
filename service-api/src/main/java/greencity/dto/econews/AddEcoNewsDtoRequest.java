package greencity.dto.econews;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AddEcoNewsDtoRequest {
    @NotEmpty
    @Size(min = 1, max = 170)
    private String title;

    @NotEmpty
    @Size(min = 20, max = 63206)
    private String text;

    @NotEmpty
    private List<String> tags;

    private String source;

    private String image;

    private String shortInfo;
}
