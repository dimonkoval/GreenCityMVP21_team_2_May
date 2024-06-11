package greencity.mapping;

import greencity.dto.event.EventAddressDto;
import greencity.entity.event.EventAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class EventAddressMapperTest {
    @InjectMocks
    EventAddressMapper mapper;

    @Test
    void convert() {
        EventAddress expected = EventAddress.builder()
                .latitude(BigDecimal.ONE)
                .longitude(BigDecimal.ZERO)
                .addressEn("english address")
                .addressUa("адреса укроїнською")
                .build();
        EventAddressDto toConvert = EventAddressDto.builder()
                .latitude(BigDecimal.ONE)
                .longitude(BigDecimal.ZERO)
                .addressEn("english address")
                .addressUa("адреса укроїнською")
                .build();
        assertEquals(expected, mapper.convert(toConvert));
    }

    @Test
    void convert_ReturnNull() {
        EventAddressDto toConvert = null;
        assertNull(mapper.convert(toConvert));
    }
}
