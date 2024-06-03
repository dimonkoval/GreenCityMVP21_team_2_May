package greencity.dto.event.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventAddress {
    BigDecimal latitude;
    BigDecimal longitude;
    private String addressEn;
    private String addressUa;
}
