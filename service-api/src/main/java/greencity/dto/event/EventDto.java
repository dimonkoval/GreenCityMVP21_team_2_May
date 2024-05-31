package greencity.dto.event;

import greencity.annotations.ValidEventDateTime;
import greencity.entity.User;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventDto {
    @NotBlank
    @Size(max = 70)
    private String title;

    @NotNull
    @ValidEventDateTime
    @Size(max = 7, min = 1)
    private List<EventDayInfoDto> eventDateTimes;

    @NotBlank
    @Size(min = 20, max = 63206)
    private String description;

    private boolean isEventOpen = true;

    private List<EventImageDto> eventImages = new ArrayList<>();

    @NotNull
    private User author;
}
