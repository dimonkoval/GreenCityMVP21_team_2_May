package greencity.service;

import greencity.constant.ErrorMessage;
import greencity.entity.Friendship;
import greencity.entity.FriendshipId;
import greencity.exception.exceptions.DuplicateDataException;
import greencity.exception.exceptions.InvalidDataException;
import greencity.exception.exceptions.NotFoundException;
import greencity.mapping.FriendshipMapper;
import greencity.repository.FriendshipRepo;
import greencity.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void addFriend(Long userId, Long friendId) {
        userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + userId));
        userRepo.findById(friendId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + friendId));

        if (userId.equals(friendId)) {
            throw new InvalidDataException("User ID and Friend ID cannot be the same");
        }

        boolean friendshipExists1 = friendshipRepo.existsById(new FriendshipId(userId, friendId));
        boolean friendshipExists2 = friendshipRepo.existsById(new FriendshipId(friendId, userId));

        if (friendshipExists1 || friendshipExists2) {
            throw new DuplicateDataException(
                    String.format("Friendship already exists between users %d and %d", userId, friendId));
        }

        Friendship friendship1 = FriendshipMapper.toEntity(userId, friendId, STATUS_FRIEND);
        Friendship friendship2 = FriendshipMapper.toEntity(friendId, userId, STATUS_FRIEND);
        friendshipRepo.save(friendship1);
        friendshipRepo.save(friendship2);
    }
}
