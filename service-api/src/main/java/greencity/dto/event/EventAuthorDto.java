package greencity.dto.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventAuthorDto {
    Long id;
    String name;
}
