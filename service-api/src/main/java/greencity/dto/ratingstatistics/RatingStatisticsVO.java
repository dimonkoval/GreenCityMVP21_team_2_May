package greencity.dto.ratingstatistics;

import greencity.annotations.RatingCalculationEnum;
import greencity.dto.user.UserVO;
import lombok.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingStatisticsVO {
    private Long id;
    private ZonedDateTime createDate;
    private RatingCalculationEnum ratingCalculationEnum;
    private double pointsChanged;
    private double rating;
    private UserVO user;
}
