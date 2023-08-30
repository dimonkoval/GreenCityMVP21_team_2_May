package greencity.dto.user;

import greencity.enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class UserProfileDtoResponse {
    private String profilePicturePath;
    private String name;
    private String city;
    private String userCredo;
    private Boolean showLocation;
    private Boolean showEcoPlace;
    private Boolean showShoppingList;
    private Float rating;
    private Role role;
}
