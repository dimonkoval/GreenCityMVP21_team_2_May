package greencity.mapping;

import greencity.dto.habit.HabitVO;
import greencity.dto.habitfact.HabitFactDtoResponse;
import greencity.dto.habitfact.HabitFactTranslationDto;
import greencity.dto.habitfact.HabitFactTranslationVO;
import greencity.dto.habitfact.HabitFactVO;
import greencity.dto.language.LanguageDTO;
import greencity.dto.language.LanguageVO;
import greencity.enums.FactOfDayStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HabitFactDtoResponseMapperTest {

    @InjectMocks
    private HabitFactDtoResponseMapper habitFactDtoResponseMapper;

    @Test
    public void convertTest() {
        HabitFactVO habitFactVO = HabitFactVO.builder()
            .id(1L)
                .translations(
                    List.of(HabitFactTranslationVO.builder()
                        .id(1L)
                        .content("content")
                        .factOfDayStatus(FactOfDayStatus.CURRENT)
                        .language(LanguageVO.builder()
                            .id(1L)
                            .code("code")
                            .build())
                        .build())
                )       
            .habit(HabitVO.builder()
                .id(1L)
                .image("habit")
                .build())
                .build();

        HabitFactDtoResponse actual = HabitFactDtoResponse.builder()
                .id(1L)
                .translations(List.of(HabitFactTranslationDto.builder()
                    .id(1L)
                    .content("content")
                    .factOfDayStatus(FactOfDayStatus.CURRENT)
                    .language(LanguageDTO.builder()
                        .id(1L)
                        .code("code")
                        .build())
                    .build()))
                .habit(HabitVO.builder()
                    .id(1L)
                    .image("habit")
                    .build())
                .build();

        HabitFactDtoResponse expected = habitFactDtoResponseMapper.convert(habitFactVO);

        assertEquals(expected.equals(actual), actual.equals(expected));
        assertEquals(expected.getId().equals(actual.getId()), actual.getId().equals(expected.getId()));
        assertEquals(expected.getHabit().equals(actual.getHabit()), actual.getHabit().equals(expected.getHabit()));
        assertEquals(expected.getTranslations().equals(actual.getTranslations()), actual.getTranslations().equals(expected.getTranslations()));
    }
}
