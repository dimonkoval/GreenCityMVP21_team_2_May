package greencity.dto.event.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
