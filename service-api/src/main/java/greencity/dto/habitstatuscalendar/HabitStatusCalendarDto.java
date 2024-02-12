package greencity.dto.habitstatuscalendar;

import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class HabitStatusCalendarDto {
    @NotEmpty
    private LocalDate enrollDate;
    @NotNull
    @Min(1)
    private Long id;
}
