package greencity.service;

import greencity.constant.ErrorMessage;
import greencity.dto.PageableDto;
import greencity.dto.friend.FriendDtoResponse;
import greencity.dto.friend.RecommendedFriendDto;
import greencity.dto.friend.RecommendedFriendProjection;
import greencity.entity.User;
import greencity.exception.exceptions.NotFoundException;
import greencity.mapping.FriendDtoMapper;
import greencity.repository.EcoNewsRepo;
import greencity.repository.FriendRepo;
import greencity.repository.HabitAssignRepo;
import greencity.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private final ModelMapper modelMapper;

    @Override
    public PageableDto<FriendDtoResponse> getUserFriends(Long userId,
                                                         String city,
                                                         boolean friendsOfFriends,
                                                         boolean filterByHabit,
                                                         Pageable pageable) {
        getUserById(userId);

        Page<User> friendsPage = friendRepo.getAllFriendsByUserId(userId, city, pageable);

        if (filterByHabit) {
            friendsPage = getFriendsByHabit(friendsPage, userId, city, pageable);
        }

        if (friendsOfFriends) {
            friendsPage = getFriendsOfFriends(friendsPage, userId, city, pageable);
        }
        return convertToPageableDto(friendsPage, userId);
    }

    @Override
    public FriendDtoResponse getFriendProfile(Long userId) {
        User user = getUserById(userId);
        return populateFriendDto(user, userId);
    }

    @Override
    public PageableDto<RecommendedFriendDto> getFriendRecommendations(Long userId, Pageable pageable) {
        Page<RecommendedFriendProjection> recommendations = friendRepo.getFriendRecommendations(userId, pageable);
        List<RecommendedFriendDto> friendDtoResponses = recommendations.stream()
                .map(friend -> modelMapper.map(friend, RecommendedFriendDto.class))
                .collect(Collectors.toList());
        return new PageableDto<>(friendDtoResponses,
                recommendations.getTotalElements(),
                recommendations.getPageable().getPageNumber(),
                recommendations.getTotalPages());
    }

    @Override
    public PageableDto<FriendDtoResponse> getRelevantFriends(Long userId, String city, Pageable pageable) {
        return convertToPageableDto(friendRepo.getRelevantFriends(userId, city, pageable), userId);
    }

    public Page<User> getFriendsByHabit(Page<User> friendsPage, Long userId, String city, Pageable pageable) {

        List<Long> habitIds = habitAssignRepo.getHabitsByUserID(userId)
                .stream()
                .map(habit -> habit.getHabit().getId())
                .collect(Collectors.toList());

        if (habitIds != null && !habitIds.isEmpty()) {
            List<User> filteredByHabit = friendsPage.getContent().stream()
                    .filter(user -> friendHasHabits(user.getId(), habitIds))
                    .collect(Collectors.toList());
            friendsPage = new PageImpl<>(filteredByHabit, pageable, filteredByHabit.size());
        }
        return friendsPage;
    }

    private boolean friendHasHabits(Long userId, List<Long> habitIds) {
        return habitAssignRepo.getHabitsByUserID(userId)
                .stream()
                .map(habit -> habit.getHabit().getId())
                .anyMatch(habitIds::contains);
    }

    private PageableDto<FriendDtoResponse> getDirectFriends(Long userId, String city, Pageable pageable) {
        return convertToPageableDto(friendRepo.getAllFriendsByUserId(userId, city, pageable), userId);
    }

    private Page<User> getFriendsOfFriends(Page<User> friendsPage, Long userId, String city, Pageable pageable) {
        List<User> listFriendsOfFriends = friendRepo.getFriendsOfFriends(userId, city, pageable);
        List<User> filteredByFriendsOfFriends = friendsPage.getContent().stream()
                .filter(listFriendsOfFriends::contains)
                .collect(Collectors.toList());

        return new PageImpl<>(filteredByFriendsOfFriends, pageable, filteredByFriendsOfFriends.size());
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
