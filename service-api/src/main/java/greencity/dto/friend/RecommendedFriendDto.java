package greencity.dto.friend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class RecommendedFriendDto {
    private Long id;
    private String name;
    private String city;
    private Double rating;
    private String profilePicturePath;
}
