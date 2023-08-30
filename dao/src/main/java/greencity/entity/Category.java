package greencity.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"categories", "places"})
@ToString(exclude = {"categories", "places"})
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(unique = true, length = 100)
    private String nameUa;

    @ManyToOne
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> categories = new ArrayList<>();
}
