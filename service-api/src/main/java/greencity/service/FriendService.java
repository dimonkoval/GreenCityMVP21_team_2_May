package greencity.service;

import greencity.dto.PageableDto;
import greencity.dto.friend.FriendDtoResponse;
import org.springframework.data.domain.Pageable;

public interface FriendService {
    PageableDto<FriendDtoResponse> getAllUserFriends(Long userId, Pageable pageable);

    FriendDtoResponse getFriendProfile(Long userId);

    PageableDto<FriendDtoResponse> searchFriends(Long userId,
                                                 boolean filterByCity,
                                                 boolean friendsOfFriends,
                                                 Pageable pageable);
}
