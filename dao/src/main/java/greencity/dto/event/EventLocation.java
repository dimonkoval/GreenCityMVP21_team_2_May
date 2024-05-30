package greencity.dto.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventLocation {

    private Long id;

    private String location;

    private String description;

//    @ManyToMany
    private EventModelDto event;

}
