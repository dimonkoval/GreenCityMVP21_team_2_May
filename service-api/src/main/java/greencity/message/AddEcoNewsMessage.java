package greencity.message;

import greencity.dto.econews.AddEcoNewsDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * Message, that is used for sending emails about adding new eco news.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddEcoNewsMessage implements Serializable {
    private AddEcoNewsDtoResponse addEcoNewsDtoResponse;
}
