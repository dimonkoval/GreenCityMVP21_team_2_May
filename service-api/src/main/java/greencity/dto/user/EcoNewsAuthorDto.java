package greencity.dto.user;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EcoNewsAuthorDto implements Serializable {
    private Long id;
    private String name;
}
