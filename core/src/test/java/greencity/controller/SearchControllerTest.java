package greencity.controller;

import greencity.dto.PageableDto;
import greencity.dto.search.SearchNewsDto;
import greencity.dto.search.SearchResponseDto;
import greencity.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @InjectMocks
    SearchController searchController;

    @Mock
    SearchService mockSearchService;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    //test search with valid request and verify response
    @Test
    void testSearchEverything_Returns200() throws Exception {
        String searchQuery = "valid query";
        Locale locale = Locale.ENGLISH;
        SearchResponseDto expectedResponse = SearchResponseDto.builder().ecoNews(Collections.emptyList()).countOfResults(0L).build();
        when(mockSearchService.search(anyString(), eq(locale.getLanguage()))).thenReturn(expectedResponse);

        ResponseEntity<SearchResponseDto> response = searchController.search(searchQuery, locale);

        assertNotNull(response, "The response can not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code must be OK (200)");
        assertEquals(expectedResponse, response.getBody(), "Response body must match expected body");
    }

    //Test search with null query and expect bad request
    @Test
    void testSearch_NullQuery_Returns400() throws Exception {
        mockMvc.perform(get("/search").locale(Locale.ENGLISH).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    //test search with long query and expect valid response
    @Test
     void testSearch_LongQuery_Returns200() throws Exception {
        String veryLongQuery = new String(new char[5001]).replace('\0', 'a');
        Locale locale = Locale.ENGLISH;

        ResponseEntity<SearchResponseDto> response = searchController.search(veryLongQuery, locale);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    //test search eco news and expect valid response
    @Test
    void testSearchEcoNews_Returns200() {
        String searchQuery = "test";
        Locale locale = Locale.US;
        Pageable pageable = null;

        SearchService mockSearchService = mock(SearchService.class);

        when(mockSearchService.searchAllNews(pageable, searchQuery, locale.getLanguage())).thenReturn(new PageableDto<>(Collections.emptyList(), 100, 1, 10));

        SearchController searchController = new SearchController(mockSearchService);

        ResponseEntity<PageableDto<SearchNewsDto>> response = searchController.searchEcoNews(pageable, searchQuery, locale);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    //test null search
    @Test
    void testSearchEcoNews_NullQuery_Returns200() {
        Pageable pageable = PageRequest.of(0, 10);
        Locale locale = Locale.US;

        ResponseEntity<PageableDto<SearchNewsDto>> response = searchController.searchEcoNews(pageable, "", locale);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}