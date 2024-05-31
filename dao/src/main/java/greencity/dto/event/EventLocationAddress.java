package greencity.dto.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventLocationAddress extends EventLocation {
    @Builder
    public EventLocationAddress() {
        super();
    }

    @Builder
    public EventLocationAddress(String address) {
        super(address);
    }
}
