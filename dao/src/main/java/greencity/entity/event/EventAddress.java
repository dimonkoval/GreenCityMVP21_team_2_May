package greencity.entity.event;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class EventAddress {
    @Column(nullable = false)
    BigDecimal latitude;

    @Column(nullable = false)
    BigDecimal longitude;

    @Column
    private String addressEn;

    @Column
    private String addressUa;
}
