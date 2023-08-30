package greencity.dto.user;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class PlaceAuthorDto implements Serializable {
    private Long id;
    private String name;
    private String email;
}
