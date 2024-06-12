package greencity.repository;

import greencity.entity.Friendship;
import greencity.entity.FriendshipId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepo extends JpaRepository<Friendship, FriendshipId> {
}
