package greencity.dto.user;

import greencity.enums.UserStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class UserStatusDto {
    @NotNull
    private Long id;

    @NotNull
    private UserStatus userStatus;
}
