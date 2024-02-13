package greencity.dto.habittranslation;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class HabitTranslationDto implements Serializable {
    private String description;
    private String habitItem;
    private String languageCode;
    private String name;
}
