package greencity.dto.econews;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@EqualsAndHashCode
public class EcoNewContentSourceDto {
    @NotEmpty
    private String content;

    private String source;
}
