package greencity.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.io.Serializable;

/**
 * Message, that is used for sending emails about not marked habits.
 */
@Getter
@ToString
@AllArgsConstructor
@Builder
public class SendHabitNotification implements Serializable {
    private String name;
    private String email;
}
