package greencity.dto.event;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventResponseDto {
    private Long id;

    private String title;

    private List<EventResponseDayInfoDto> dateTimes;

    private String description;

    private boolean isOpen;

    private List<EventImageDto> images;

    private EventAuthorDto author;

    private List<String> tagsUa;

    private List<String> tagsEn;
}
