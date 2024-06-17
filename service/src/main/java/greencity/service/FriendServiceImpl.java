package greencity.service;

import greencity.constant.ErrorMessage;
import greencity.dto.PageableDto;
import greencity.dto.friend.FriendDtoResponse;
import greencity.entity.User;
import greencity.exception.exceptions.NotFoundException;
import greencity.mapping.FriendDtoMapper;
import greencity.repository.EcoNewsRepo;
import greencity.repository.FriendRepo;
import greencity.repository.HabitAssignRepo;
import greencity.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {
    private static final String STATUS_ACQUIRED = "ACQUIRED";
    private static final String STATUS_INPROGRESS = "INPROGRESS";
    private final UserRepo userRepo;
    private final FriendRepo friendRepo;
    private final HabitAssignRepo habitAssignRepo;
    private final EcoNewsRepo ecoNewsRepo;
    private final FriendDtoMapper mapper;

    @Override
    public PageableDto<FriendDtoResponse> getAllUserFriends(Long userId, Pageable pageable) {
        User userFound = getUserById(userId);

        List<Long> habitIds = habitAssignRepo.getHabitsByUserID(userId)
                .stream()
                .map(habit -> habit.getHabit().getId())
                .collect(Collectors.toList());

        Page<User> friendsPage = friendRepo.getAllFriendsByUserId(userId, userFound.getCity(), habitIds, pageable);
        return convertToPageableDto(friendsPage, userId);
    }

    @Override
    public FriendDtoResponse getFriendProfile(Long userId) {
        User user = getUserById(userId);
        return populateFriendDto(user, userId);
    }

    @Override
    public PageableDto<FriendDtoResponse>
        searchFriends(Long userId, boolean filterByCity, boolean friendsOfFriends, Pageable pageable) {
        User user = getUserById(userId);

        String city = (filterByCity ? user.getCity() : null);

        if (friendsOfFriends) {
            return searchFriendsOfFriends(userId, city, pageable);
        }
        return searchDirectFriends(userId, city, pageable);
    }

    private PageableDto<FriendDtoResponse> searchDirectFriends(Long userId, String city, Pageable pageable) {
        return convertToPageableDto(friendRepo.searchDirectFriends(userId, city, pageable), userId);
    }

    private PageableDto<FriendDtoResponse> searchFriendsOfFriends(Long userId, String city, Pageable pageable) {
        return convertToPageableDto(friendRepo.searchFriendsOfFriends(userId, city, pageable), userId);
    }

    private PageableDto<FriendDtoResponse> convertToPageableDto(Page<User> friendsPage, Long userId) {
        List<FriendDtoResponse> friendDtoResponses = friendsPage.stream()
                .map(user -> populateFriendDto(user, userId))
                .collect(Collectors.toList());
        return new PageableDto<>(friendDtoResponses,
                friendsPage.getTotalElements(),
                friendsPage.getPageable().getPageNumber(),
                friendsPage.getTotalPages());
    }

    private User getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + userId));
    }

    private FriendDtoResponse populateFriendDto(User user, Long userId) {
        FriendDtoResponse friendDtoResponse = mapper.convert(user);
        if (friendDtoResponse != null) {
            friendDtoResponse.setMutualFriends(
                    friendRepo.findMutualFriends(userId, user.getId()).size());
            friendDtoResponse.setAmountHabitsAcquired(
                    habitAssignRepo.getHabitAssignByUserIdAndStatus(user.getId(), STATUS_ACQUIRED).size());
            friendDtoResponse.setAmountHabitsInProgress(
                    habitAssignRepo.getHabitAssignByUserIdAndStatus(user.getId(), STATUS_INPROGRESS).size());
            friendDtoResponse.setAmountNewsPublished(
                    ecoNewsRepo.findAllByUserId(user.getId()).size());
        }
        return friendDtoResponse;
    }
}
