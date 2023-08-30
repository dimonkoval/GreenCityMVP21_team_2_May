package greencity.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "habit_translation")
@EqualsAndHashCode(
    exclude = {"habit"})
@ToString(
    exclude = {"habit", "language"})
public class HabitTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String habitItem;

    @ManyToOne
    private Language language;

    @ManyToOne
    private Habit habit;
}
