package greencity.dto.user;

import lombok.*;

import javax.validation.constraints.NotEmpty;

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
