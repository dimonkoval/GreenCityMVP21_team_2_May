package greencity.filters;

import greencity.dto.habit.HabitDto;
import greencity.dto.habitfact.HabitFactDto;
import greencity.entity.*;
import greencity.entity.HabitFactTranslation_;
import greencity.entity.HabitFact_;
import greencity.entity.Habit_;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitFactSpecificationTest {

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<HabitFact> criteriaQuery;

    @Mock
    private Root<HabitFact> root;

    @Mock
    private Path<Object> objectPath;

    @Mock
    private Path<String> stringPath;

    @Mock
    private Path<Long> longPath;

    @Mock
    private Path<HabitFact> habitFactPath;

    @Mock
    private Root<HabitFactTranslation> habitFactTranslationRoot;

    @Mock
    private Predicate expected;

    private List<SearchCriteria> searchCriteriaList;

    private HabitFactSpecification habitFactSpecification;

    @BeforeEach
    void init() {
        HabitDto habitDto = HabitDto.builder().id(2L).build();
        HabitFactDto habitFactDto = HabitFactDto.builder()
                .id(1L)
                .content("content")
                .habit(habitDto)
                .build();

        searchCriteriaList = new ArrayList<>();
        searchCriteriaList.add(SearchCriteria.builder()
                .key(HabitFact_.ID)
                .type(HabitFact_.ID)
                .value(habitFactDto.getId())
                .build());
        searchCriteriaList.add(SearchCriteria.builder()
                .key(HabitFactTranslation_.CONTENT)
                .type(HabitFactTranslation_.CONTENT)
                .value(habitFactDto.getContent())
                .build());
        searchCriteriaList.add(SearchCriteria.builder()
                .key(Habit_.ID)
                .type(Habit_.ID)
                .value(habitFactDto.getHabit().getId())
                .build());

        habitFactSpecification = new HabitFactSpecification(searchCriteriaList);
    }

    @Test
    void toPredicate() {
        // Setup for conjunction
        when(criteriaBuilder.conjunction()).thenReturn(expected);

        // id
        SearchCriteria idSearchCriteria = searchCriteriaList.get(0);
        when(root.get(idSearchCriteria.getKey())).thenReturn(objectPath);
        when(criteriaBuilder.equal(objectPath, idSearchCriteria.getValue())).thenReturn(expected);

        // content
        SearchCriteria contentSearchCriteria = searchCriteriaList.get(1);
        when(criteriaQuery.from(HabitFactTranslation.class)).thenReturn(habitFactTranslationRoot);
        // if true
        when(criteriaBuilder.conjunction()).thenReturn(expected);
        // if false
        when(habitFactTranslationRoot.get(HabitFactTranslation_.content)).thenReturn(stringPath);
        when(criteriaBuilder.like(any(), eq("%" + contentSearchCriteria.getValue() + "%"))).thenReturn(expected);
        when(habitFactTranslationRoot.get(HabitFactTranslation_.habitFact)).thenReturn(habitFactPath);
        when(habitFactPath.get(HabitFact_.id)).thenReturn(longPath);
        when(root.get(HabitFact_.id)).thenReturn(longPath);
        when(criteriaBuilder.equal(longPath, longPath)).thenReturn(expected);

        // habitId
        SearchCriteria habitIdSearchCriteria = searchCriteriaList.get(2);
        when(criteriaBuilder.equal(objectPath, habitIdSearchCriteria.getValue())).thenReturn(expected);

        // Combining predicates
        when(criteriaBuilder.and(any(Predicate.class), any(Predicate.class))).thenReturn(expected);

        Predicate actual = habitFactSpecification.toPredicate(root, criteriaQuery, criteriaBuilder);
        assertEquals(expected, actual);
    }
}