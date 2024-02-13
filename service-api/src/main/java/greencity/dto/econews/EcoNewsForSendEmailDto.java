package greencity.dto.econews;

import greencity.dto.user.PlaceAuthorDto;
import lombok.*;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class EcoNewsForSendEmailDto {
    private String unsubscribeToken;

    private ZonedDateTime creationDate;

    private String imagePath;

    private String source;

    private PlaceAuthorDto author;

    private String title;

    private String text;
}
