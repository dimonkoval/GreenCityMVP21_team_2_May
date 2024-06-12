package greencity.dto.event;

import greencity.annotations.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventRequestSaveDto {
    @NotBlank(message = "Title cannot be blank")
    @Size(message = "Title must be a maximum of {max} characters", max = 70)
    private String title;

    @ValidEventDayInfo
    @Size(max = 7, message = "Event must have not more than {max} days")
    private List<EventSaveDayInfoDto> daysInfo;

    @NotBlank
    @Size(min = 20, max = 63206, message = "Description must be at least {min} and maximum {max} characters")
    private String description;

    private boolean isOpen = true;

    private int mainImageNumber = 0;

    private List<String> tags;
}
