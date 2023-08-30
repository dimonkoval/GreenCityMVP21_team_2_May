package greencity.dto.habit;

import greencity.dto.habitstatuscalendar.HabitStatusCalendarDto;
import greencity.dto.user.UserShoppingListItemAdvanceDto;
import greencity.enums.HabitAssignStatus;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@ToString
public class HabitAssignDto {
    private ZonedDateTime createDateTime;
    private Integer duration;
    private HabitDto habit;
    private List<UserShoppingListItemAdvanceDto> userShoppingListItems;
    private List<HabitStatusCalendarDto> habitStatusCalendarDtoList;
    private Integer habitStreak;
    private Long id;
    private ZonedDateTime lastEnrollmentDate;
    private HabitAssignStatus status;
    private Long userId;
    private Integer workingDays;
    private Boolean progressNotificationHasDisplayed;
}