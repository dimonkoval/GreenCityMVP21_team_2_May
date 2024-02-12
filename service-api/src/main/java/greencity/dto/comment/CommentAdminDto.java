package greencity.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentAdminDto {
    private Long id;
    private String text;
    private LocalDateTime createdDate;
}
