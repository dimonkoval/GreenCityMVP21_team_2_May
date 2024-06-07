package greencity.mapping;

import greencity.dto.event.EventAddressDto;
import greencity.dto.event.model.EventAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class EventAddressDtoMapperTest {
    @InjectMocks
    EventAddressDtoMapper mapper;

    @Test
    void convert() {
        EventAddressDto expected = EventAddressDto.builder()
                .latitude(BigDecimal.TEN)
                .longitude(BigDecimal.TWO)
                .addressEn("some address")
                .addressUa("якась адреса")
                .build();
        EventAddress toConvert = EventAddress.builder()
                .latitude(BigDecimal.TEN)
                .longitude(BigDecimal.TWO)
                .addressEn("some address")
                .addressUa("якась адреса")
                .build();
        assertEquals(expected, mapper.convert(toConvert));
    }

    @Test
    void convert_ReturnNull() {
        EventAddress toConvert = null;
        assertNull(mapper.convert(toConvert));
    }
}
