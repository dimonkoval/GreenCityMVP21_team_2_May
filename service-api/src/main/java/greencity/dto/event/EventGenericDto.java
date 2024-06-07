package greencity.dto.event;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventGenericDto {
    private Long id;

    private String title;

    private EventResponseDayInfoDto firstDayInfo;

    private boolean isOpen;

    private EventImageDto image;

    private EventAuthorDto author;
}
