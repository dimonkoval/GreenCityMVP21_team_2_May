package greencity.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Builder
@Table(name = "habit_status_calendar")
@Entity
public class HabitStatusCalendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enroll_date")
    private LocalDate enrollDate;

    @ManyToOne
    private HabitAssign habitAssign;
}
