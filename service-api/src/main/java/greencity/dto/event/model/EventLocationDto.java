package greencity.dto.event.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventLocationDto {

    private String location;

    private String description;
}
