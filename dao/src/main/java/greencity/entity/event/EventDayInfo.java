package greencity.entity.event;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "event_day_infos")
@ToString(exclude = {"id", "event"})
@EqualsAndHashCode(exclude = {"id", "event"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EventDayInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean isAllDay;

    @Column(nullable = false)
    private ZonedDateTime startDateTime;

    @Column(nullable = false)
    private ZonedDateTime endDateTime;

    @Column
    private int dayNumber;

    @Column
    private String link;

    @Embedded
    private EventAddress address;

    @ManyToOne
    private Event event;
}
