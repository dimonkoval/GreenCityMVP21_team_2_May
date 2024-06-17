package greencity.service;

import greencity.dto.user.UserVO;

public interface FriendshipService {
    String addFriend(Long userId, Long friendId, UserVO user);

    String deleteFriend(Long userId, Long friendId, UserVO user);
}
