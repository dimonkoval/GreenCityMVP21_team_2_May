package greencity.dto.event;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventAddressDto {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String addressUa;
    private String addressEn;
}
