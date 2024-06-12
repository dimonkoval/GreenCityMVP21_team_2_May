package greencity.entity.event;

import greencity.entity.Tag;
import greencity.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@ToString(exclude = {"author", "tags", "dayInfos", "images"})
@EqualsAndHashCode(exclude = {"author", "tags", "dayInfos", "images"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventDayInfo> dayInfos;

    @Column(nullable = false)
    private String description;

    @Column
    private boolean isOpen = true;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @ManyToMany
    @JoinTable(name = "event_tags",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
}
