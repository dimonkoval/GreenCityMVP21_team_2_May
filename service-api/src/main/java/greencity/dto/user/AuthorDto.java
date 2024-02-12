package greencity.dto.user;

import lombok.*;
import jakarta.validation.constraints.NotEmpty;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AuthorDto {
    @NotEmpty
    private Long id;

    @NotEmpty
    private String name;
}
