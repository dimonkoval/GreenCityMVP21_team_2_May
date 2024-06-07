package greencity.entity;

import greencity.dto.event.model.EventModelDto;
import greencity.entity.localization.TagTranslation;
import greencity.enums.TagType;
import lombok.*;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "tags")
@ToString(exclude = {"ecoNews", "habits", "events"})
@EqualsAndHashCode(exclude = {"ecoNews", "habits", "events"})
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TagType type;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<TagTranslation> tagTranslations;

    @ManyToMany(mappedBy = "tags")
    private List<EcoNews> ecoNews;

    @ManyToMany(mappedBy = "tags")
    private Set<Habit> habits;

   // @ManyToMany
   // private Set<Event> events;
}
