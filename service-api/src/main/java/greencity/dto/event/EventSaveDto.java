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
public class EventSaveDto {
    @NotBlank
    @Size(max = 70)
    private String title;

    @NotNull
    @ValidEventDateTime
    //@ValidLocation
    @Size(max = 7, min = 1)
    private List<EventSaveDayInfoDto> daysInfo;

    @NotBlank
    @Size(min = 20, max = 63206)
    private String description;

    private boolean isOpen = true;
}
