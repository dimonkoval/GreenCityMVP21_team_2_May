package greencity.service;

import greencity.dto.PageableDto;
import greencity.dto.friend.FriendDtoResponse;
import greencity.dto.friend.RecommendedFriendDto;
import org.springframework.data.domain.Pageable;

public interface FriendService {
    PageableDto<FriendDtoResponse> getUserFriends(
            Long userId, String city, boolean friendsOfFriends, boolean filterByHabit, Pageable pageable);

    FriendDtoResponse getFriendProfile(Long userId);

    PageableDto<RecommendedFriendDto> getFriendRecommendations(Long userId, Pageable pageable);

    PageableDto<FriendDtoResponse> getRelevantFriends(Long userId, String city, Pageable pageable);
}
