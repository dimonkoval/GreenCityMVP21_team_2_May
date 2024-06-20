package greencity.service;

import greencity.constant.ErrorMessage;
import greencity.dto.user.UserVO;
import greencity.entity.Friendship;
import greencity.entity.FriendshipId;
import greencity.enums.Role;
import greencity.exception.exceptions.DuplicateDataException;
import greencity.exception.exceptions.InvalidDataException;
import greencity.exception.exceptions.NotFoundException;
import greencity.mapping.FriendshipMapper;
import greencity.repository.FriendshipRepo;
import greencity.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private static  final String STATUS_FRIEND = "FRIEND";
    @Autowired
    private FriendshipRepo friendshipRepo;
    private UserRepo userRepo;

    @Override
    @Transactional
    public String addFriend(Long userId, Long friendId, UserVO user) {
        validateAdminOrCurrentUser(userId, user);
        validateUsersForFriendship(userId, friendId);

        checkFriendshipExistsOrThrow(userId, friendId);
        checkFriendshipExistsOrThrow(friendId, userId);

        friendshipRepo.save(FriendshipMapper.toEntity(userId, friendId, STATUS_FRIEND));
        friendshipRepo.save(FriendshipMapper.toEntity(friendId, userId, STATUS_FRIEND));

        return String.format("Friend by ID %d added successfully", friendId);
    }

    @Override
    public String deleteFriend(Long userId, Long friendId, UserVO user) {
        validateAdminOrCurrentUser(userId, user);
        validateUsersForFriendship(userId, friendId);

        Friendship friendship1 = getFriendshipOrThrow(userId, friendId);
        Friendship friendship2 = getFriendshipOrThrow(friendId, userId);

        friendshipRepo.delete(friendship1);
        friendshipRepo.delete(friendship2);

        return String.format("Friend by ID %d has been successfully unfriended", friendId);
    }

    private void validateAdminOrCurrentUser(Long userId, UserVO user) {
        if (user.getRole() != Role.ROLE_ADMIN && !userId.equals(user.getId())) {
            throw new AccessDeniedException(ErrorMessage.ACCESS_DENIED_MESSAGE);
        }
    }

    private void validateUsersForFriendship(Long userId, Long friendId) {
        userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + userId));
        userRepo.findById(friendId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + friendId));

        if (userId.equals(friendId)) {
            throw new InvalidDataException("User ID and Friend ID cannot be the same");
        }
    }

    private Friendship getFriendshipOrThrow(Long userId, Long friendId) {
        return friendshipRepo.findById(new FriendshipId(userId, friendId))
                .orElseThrow(() -> new NotFoundException(
                        String.format("Friendship not found between user %d and friend %d", userId, friendId)));
    }

    public void checkFriendshipExistsOrThrow(Long userId, Long friendId) {
        boolean friendshipExists = friendshipRepo.existsById(new FriendshipId(userId, friendId));
        if (friendshipExists) {
            throw new DuplicateDataException(
                    String.format("Friendship already exists between users %d and %d", userId, friendId));
        }
    }
}
