package greencity.entity;

import greencity.entity.event.Event;
import greencity.enums.CommentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events_comments")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class EventComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 1, max = 8000)
    private String text;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Event event;

    @ManyToOne
    private EventComment parentComment;

    @Builder.Default
    @OneToMany(mappedBy = "parentComment", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<EventComment> comments = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentStatus status;
}
