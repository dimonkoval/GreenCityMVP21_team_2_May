package greencity.dto.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
public class EventLocationLink extends EventLocation {
    @Builder
    public EventLocationLink() {
        super();
    }

    @Builder
    public EventLocationLink(String link) {
        super(link);
    }
}
