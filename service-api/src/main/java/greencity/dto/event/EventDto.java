package greencity.dto.event;

import greencity.annotations.ValidEventDateTime;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.ZonedDateTime;
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
    String title;

    @Max(7)
    @Min(1)
    int daysDuration;

    @NotNull
    @ValidEventDateTime
    List<ZonedDateTime> dateTime;

    @NotEmpty
    String location;
    @NotEmpty
    String description;
}
