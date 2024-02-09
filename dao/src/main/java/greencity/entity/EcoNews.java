package greencity.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "eco_news")
@ToString(exclude = {"author", "tags"})
@EqualsAndHashCode(exclude = {"author", "tags"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EcoNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private ZonedDateTime creationDate;

    @Column
    private String imagePath;

    @Column
    private String source;
    @Column
    private String shortInfo;

    @ManyToOne
    private User author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @OneToMany(mappedBy = "ecoNews", fetch = FetchType.LAZY)
    private List<EcoNewsComment> ecoNewsComments = new ArrayList<>();

    @ManyToMany
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(
        name = "eco_news_users_likes",
        joinColumns = @JoinColumn(name = "eco_news_id"),
        inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> usersLikedNews = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "eco_news_users_dislikes",
        joinColumns = @JoinColumn(name = "eco_news_id"),
        inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<User> usersDislikedNews = new HashSet<>();
}
