package greencity.dto.event;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public abstract class EventLocation {

    @Column
    protected String location;
}
