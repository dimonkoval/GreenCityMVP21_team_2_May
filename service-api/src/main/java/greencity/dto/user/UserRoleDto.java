package greencity.dto.user;

import greencity.enums.Role;
import lombok.*;

import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserRoleDto {
    @NotNull
    private Role role;
}
