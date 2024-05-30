package greencity.dto.event;

import greencity.annotations.ValidEventDateTime;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventDto {
    @NotEmpty
    @Size(max = 70)
    private String title;

    @Max(7)
    @Min(1)
    private int daysDuration;

    @NotNull
    @ValidEventDateTime
    @Size(max = 7, min = 1)
    private List<EventDateTime> eventDateTimes;

    @NotEmpty
    private String location;
    @NotEmpty
    private String description;
}
