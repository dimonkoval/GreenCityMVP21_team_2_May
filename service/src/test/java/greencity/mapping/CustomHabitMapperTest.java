package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.habit.AddCustomHabitDtoRequest;
import greencity.entity.Habit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomHabitMapperTest {
    @InjectMocks
    CustomHabitMapper mapper;

    @Test
    void convert() {
        Habit expected = ModelUtils.getHabit();

        AddCustomHabitDtoRequest dtoToBeConverted = AddCustomHabitDtoRequest.builder()
                .image(expected.getImage())
                .complexity(expected.getComplexity())
                .defaultDuration(expected.getDefaultDuration())
                .build();

        assertEquals(expected, mapper.convert(dtoToBeConverted));
    }
}