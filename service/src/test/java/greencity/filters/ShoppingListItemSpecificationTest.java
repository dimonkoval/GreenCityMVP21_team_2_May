package greencity.filters;

import greencity.dto.shoppinglistitem.ShoppingListItemDto;
import greencity.entity.ShoppingListItem;
import greencity.entity.ShoppingListItem_;
import greencity.entity.Translation_;
import greencity.entity.localization.ShoppingListItemTranslation;
import greencity.entity.localization.ShoppingListItemTranslation_;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingListItemSpecificationTest {

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    CriteriaQuery<ShoppingListItem> criteriaQuery;

    @Mock
    Root<ShoppingListItem> root;

    @Mock
    private Path<Object> objectPath;

    @Mock
    private Path<String> stringPath;

    @Mock
    private Path<Long> longPath;

    @Mock
    private Path<ShoppingListItem> shoppingListItemPath;

    @Mock
    Root<ShoppingListItemTranslation> itemTranslationRoot;

    @Mock
    private Predicate expected;

    List<SearchCriteria> searchCriteriaList;

    ShoppingListItemSpecification shoppingListItemSpecification;

    @BeforeEach
    void init() {
        ShoppingListItemDto dto = new ShoppingListItemDto(1L, "text", "status");
        searchCriteriaList = new ArrayList<>();
        searchCriteriaList.add(SearchCriteria.builder()
                .key(Translation_.ID)
                .type(Translation_.ID)
                .value(dto.getId())
                .build());
        searchCriteriaList.add(SearchCriteria.builder()
                .key(Translation_.CONTENT)
                .type(Translation_.CONTENT)
                .value(dto.getText())
                .build());

        shoppingListItemSpecification = new ShoppingListItemSpecification(searchCriteriaList);
    }


    @Test
    void toPredicate() {
        //method start
        when(criteriaBuilder.conjunction()).thenReturn(expected);

        //id
        SearchCriteria idSearchCriteria = searchCriteriaList.get(0);
        when(root.get(idSearchCriteria.getKey())).thenReturn(objectPath);
        when(criteriaBuilder.equal(objectPath, idSearchCriteria.getValue())).thenReturn(expected);

        //content
        SearchCriteria contentSearchCriteria = searchCriteriaList.get(1);
        when(criteriaQuery.from(ShoppingListItemTranslation.class)).thenReturn(itemTranslationRoot);
        //if true
        when(criteriaBuilder.conjunction()).thenReturn(expected);
        //if false
        when(itemTranslationRoot.get(Translation_.content)).thenReturn(stringPath);
        when(criteriaBuilder.like(any(), eq("%" + contentSearchCriteria.getValue() + "%"))).thenReturn(expected);
        when(itemTranslationRoot.get(ShoppingListItemTranslation_.shoppingListItem)).thenReturn(shoppingListItemPath);
        when(shoppingListItemPath.get(ShoppingListItem_.id)).thenReturn(longPath);
        when(root.get(ShoppingListItem_.id)).thenReturn(longPath);
        when(criteriaBuilder.equal(longPath, longPath)).thenReturn(expected);
        when(criteriaBuilder.and(expected, expected)).thenReturn(expected);

        Predicate actual = shoppingListItemSpecification.toPredicate(root, criteriaQuery, criteriaBuilder);
        assertEquals(expected, actual);
    }
}