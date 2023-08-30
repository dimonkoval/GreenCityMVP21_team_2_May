package greencity.dto.category;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class CategoryDtoResponse {
    private Long id;
    private String name;
}
