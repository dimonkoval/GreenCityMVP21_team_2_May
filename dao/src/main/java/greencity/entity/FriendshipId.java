package greencity.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record FriendshipId(Long userId, Long friendId){}
