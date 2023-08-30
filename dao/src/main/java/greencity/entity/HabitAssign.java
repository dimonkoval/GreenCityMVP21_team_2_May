package greencity.entity;

import greencity.enums.HabitAssignStatus;
import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
@Table(name = "habit_assign")
public class HabitAssign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "create_date", nullable = false)
    private ZonedDateTime createDate;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private HabitAssignStatus status;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "working_days", nullable = false)
    private Integer workingDays;

    @Column(name = "habit_streak", nullable = false)
    private Integer habitStreak;

    @Column(name = "last_enrollment", nullable = false)
    private ZonedDateTime lastEnrollmentDate;

    /**
     * This variable shows that the progress notification has displayed and habit
     * has enough progress (from 80 to 100 %) to be in status ACQUIRED. Now user can
     * change status from INPROGRESS to ACQUIRED or continue to enroll habit to
     * 100%.
     *
     */
    @Column(name = "progress_notification_has_displayed", nullable = false)
    private Boolean progressNotificationHasDisplayed;

    @OneToMany(mappedBy = "habitAssign", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserShoppingListItem> userShoppingListItems;

    @ManyToOne(fetch = FetchType.LAZY)
    private Habit habit;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "habitAssign", cascade = CascadeType.ALL)
    private List<HabitStatistic> habitStatistic;

    @OneToMany(mappedBy = "habitAssign", cascade = CascadeType.ALL)
    private List<HabitStatusCalendar> habitStatusCalendars;
}
