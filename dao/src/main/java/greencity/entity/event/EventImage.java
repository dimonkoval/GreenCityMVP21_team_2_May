package greencity.entity.event;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "event_images")
@ToString(exclude = {"id", "event"})
@EqualsAndHashCode(exclude = {"id", "event"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EventImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private boolean isMain;

    @ManyToOne
    private Event event;
}
