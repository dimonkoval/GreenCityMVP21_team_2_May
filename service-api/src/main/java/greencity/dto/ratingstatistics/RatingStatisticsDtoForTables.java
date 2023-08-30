package greencity.dto.ratingstatistics;

import lombok.*;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class RatingStatisticsDtoForTables {
    private Long id;
    private ZonedDateTime createDate;
    private String eventName;
    private float pointsChanged;
    private float rating;
    private long userId;
    private String userEmail;
}
