package greencity.dto.habitstatistic;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class HabitLogItemDto {
    private String habitItemName;
    private Integer habitItemAmount;
}
