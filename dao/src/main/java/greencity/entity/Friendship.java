package greencity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Table(name = "users_friends")
public class Friendship {
    @EmbeddedId
    private FriendshipId pk;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    public Long getUserId() {
        return pk.userId();
    }

    public Long getFriendId() {
        return pk.friendId();
    }
}
