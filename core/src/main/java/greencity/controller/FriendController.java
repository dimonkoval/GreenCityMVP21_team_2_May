package greencity.controller;

import greencity.annotations.ApiLocale;
import greencity.annotations.ApiPageableWithLocale;
import greencity.annotations.CurrentUser;
import greencity.constant.HttpStatuses;
import greencity.dto.PageableDto;
import greencity.dto.friend.FriendDtoResponse;
import greencity.dto.friend.RecommendedFriendDto;
import greencity.dto.user.UserVO;
import greencity.service.FriendService;
import greencity.service.FriendshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/friends")
public class FriendController {
    private final FriendService friendService;
    private final FriendshipService friendshipService;

    @Operation(summary = "Get user's friends by filters")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
        @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
        @ApiResponse(responseCode = "401", description = HttpStatuses.UNAUTHORIZED),
        @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND),
    })
    @GetMapping("/{userId}")
    @ApiPageableWithLocale
    public ResponseEntity<PageableDto<FriendDtoResponse>> getFriendsByUserID(
            @PathVariable Long userId,
            @RequestParam(required = false) String city,
            @RequestParam(required = false, defaultValue = "false") boolean friendsOfFriends,
            @RequestParam(required = false, defaultValue = "false") boolean filterByHabit,
            @Parameter(hidden = true)
            @PageableDefault(size = 6) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                friendService.getUserFriends(userId, city, friendsOfFriends,
                        filterByHabit, pageable));
    }

    @Operation(summary = "Getting the most relevant friends, based on commenting and personal rating")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
        @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
        @ApiResponse(responseCode = "401", description = HttpStatuses.UNAUTHORIZED),
        @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND),
    })
    @GetMapping("/relevantFriends/{userId}")
    @ApiPageableWithLocale
    public ResponseEntity<PageableDto<FriendDtoResponse>> getRelevantFriends(
            @PathVariable Long userId,
            @RequestParam(required = false) String city,
            @Parameter(hidden = true)
            @PageableDefault(size = 6) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                friendService.getRelevantFriends(userId, city, pageable));
    }

    @Operation(summary = "Getting info about a friend with user attributes, habit counters, and mutual friends")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
        @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
        @ApiResponse(responseCode = "401", description = HttpStatuses.UNAUTHORIZED),
        @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND),
    })
    @GetMapping("/profile/{userId}")
    @ApiLocale
    public ResponseEntity<FriendDtoResponse> getFriendProfile(
            @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                friendService.getFriendProfile(userId));
    }

    @Operation(summary = "Gets recommended friends")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
        @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
        @ApiResponse(responseCode = "401", description = HttpStatuses.UNAUTHORIZED),
        @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND),
    })
    @GetMapping("/recommendations/{userId}")
    @ApiPageableWithLocale
    public ResponseEntity<PageableDto<RecommendedFriendDto>> getFriendRecommendations(
        @PathVariable Long userId,
        @Parameter(hidden = true)Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                friendService.getFriendRecommendations(userId, pageable));
    }

    @Operation(summary = "Adding a new friend")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
        @ApiResponse(responseCode = "201", description = HttpStatuses.CREATED),
        @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
        @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND),
    })
    @PostMapping("/{userId}")
    @ApiLocale
    public ResponseEntity<String> addFriend(
        @PathVariable Long userId,
        @RequestParam Long friendId,
        @Parameter(hidden = true) @CurrentUser UserVO user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(friendshipService.addFriend(userId, friendId, user));
    }

    @Operation(summary = "Remove friend from friend list")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
        @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
        @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND),
    })
    @DeleteMapping()
    @ApiLocale
    public ResponseEntity<String> deleteFriend(
            @RequestParam("userId") Long userId,
            @RequestParam("friendId") Long friendId,
            @Parameter(hidden = true) @CurrentUser UserVO user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(friendshipService.deleteFriend(userId, friendId, user));
    }
}
