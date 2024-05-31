package greencity.dto.event;

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

    private String onlineLink;
}
