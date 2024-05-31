package greencity.dto.event.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public abstract class EventLocation {

    //@Column
    protected String location;
}
