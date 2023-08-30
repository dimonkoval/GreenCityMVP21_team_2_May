package greencity.dto.tag;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Builder
@ToString
public class NewTagDto {
    private Long id;
    private String name;
    private String nameUa;
}
