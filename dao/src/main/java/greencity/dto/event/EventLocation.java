package greencity.dto.event;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventLocation {

    private Long id;

    @Column
    @Nullable
    private String location;

    @Column
    @Nullable
    private String description;

    @Column
    @Nullable
    private String onlineLink;

//    @ManyToMany
    private EventModelDto event;

}
