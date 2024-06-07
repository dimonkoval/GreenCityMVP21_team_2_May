package greencity.dto.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventImageDto {

    private String imagePath;

    private boolean isMain;

}
