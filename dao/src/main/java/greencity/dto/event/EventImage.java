package greencity.dto.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventImage {
    private Long id;

    private String imagePath;

    private boolean isMain;

//    @ManyToOne
    private EventModelDto event;
}
