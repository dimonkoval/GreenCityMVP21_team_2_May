package greencity.repository;

import greencity.dto.friend.RecommendedFriendProjection;
import greencity.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FriendRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT u.*, uf.created_date "
            + "FROM users_friends uf "
            + "JOIN users u ON uf.friend_id = u.id "
            + "WHERE uf.user_id = :userId "
            + "AND (:city IS NULL OR u.city = :city)")
    Page<User> getAllFriendsByUserId(@Param("userId") Long userId,
                                     @Param("city") String city,
                                     Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT DISTINCT u.* "
            + "FROM users u "
            + "JOIN users_friends uf1 ON u.id = uf1.friend_id "
            + "JOIN users_friends uf2 ON u.id = uf2.friend_id "
            + "WHERE uf1.user_id = :userId1 AND uf2.user_id = :userId2")
    List<User> findMutualFriends(@Param("userId1") Long userId1,
                                 @Param("userId2") Long userId2);

    @Query(nativeQuery = true, value = "SELECT DISTINCT u.* "
            + "FROM users_friends uf "
            + "JOIN users_friends uf2 ON uf.friend_id = uf2.user_id "
            + "JOIN users u ON u.id = uf2.friend_id "
            + "WHERE uf.user_id = :userId "
            + "AND u.id <> :userId "
            + "AND (:city IS NULL OR u.city = :city)")
    List<User> getFriendsOfFriends(@Param("userId")Long userId,
                                   @Param("city")String city,
                                   Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT DISTINCT u.*, uf.created_date, "
            + "CASE WHEN ec.eco_news_id IS NOT NULL AND ec.eco_news_id IN "
            + "(SELECT eco_news_id FROM econews_comment WHERE user_id = :userId) "
            + "THEN 1 ELSE 2 END AS relevance_order "
            + "FROM users_friends uf JOIN users u ON uf.friend_id = u.id "
            + "LEFT JOIN econews_comment ec ON ec.user_id = u.id "
            + "WHERE uf.user_id = :userId AND (:city IS NULL OR u.city = :city) "
            + "ORDER BY relevance_order, u.rating DESC, uf.created_date DESC")
    Page<User> getRelevantFriends(@Param("userId")Long userId,
                                  @Param("city")String city,
                                  Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT  id, name, city, rating, profile_picture AS profilePicturePath "
            + "FROM public.fn_recommended_friends(:userId)")
    Page<RecommendedFriendProjection> getFriendRecommendations(@Param("userId") Long userId, Pageable pageable);
}
