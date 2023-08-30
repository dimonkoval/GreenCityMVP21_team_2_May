package greencity.dto.user;

import greencity.dto.econews.EcoNewsVO;
import greencity.dto.econewscomment.EcoNewsCommentVO;
import greencity.dto.language.LanguageVO;
import greencity.dto.ownsecurity.OwnSecurityVO;
import greencity.dto.shoppinglistitem.CustomShoppingListItemVO;
import greencity.dto.verifyemail.VerifyEmailVO;
import greencity.enums.EmailNotification;
import greencity.enums.Role;
import greencity.enums.UserStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserVO {
    private Long id;

    private String name;

    private String email;

    private Role role;

    private String userCredo;

    private UserStatus userStatus;

    private List<UserShoppingListItemVO> userShoppingListItemVOS = new ArrayList<>();

    private List<CustomShoppingListItemVO> customShoppingListItemVOS = new ArrayList<>();

    private VerifyEmailVO verifyEmail;

    private Double rating;

    private EmailNotification emailNotification;

    private LocalDateTime dateOfRegistration;

    private List<UserVO> userFriends = new ArrayList<>();

    private String refreshTokenKey;

    private OwnSecurityVO ownSecurity;

    private String profilePicturePath;

    private Set<EcoNewsVO> ecoNewsLiked;

    private Set<EcoNewsCommentVO> ecoNewsCommentsLiked;

    private String firstName;

    private String city;

    private Boolean showLocation;

    private Boolean showEcoPlace;

    private Boolean showShoppingList;

    private LocalDateTime lastActivityTime;

    private LanguageVO languageVO;
}
