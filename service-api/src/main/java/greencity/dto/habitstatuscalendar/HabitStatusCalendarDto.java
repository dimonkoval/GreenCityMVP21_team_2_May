package greencity.dto.habitstatuscalendar;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
