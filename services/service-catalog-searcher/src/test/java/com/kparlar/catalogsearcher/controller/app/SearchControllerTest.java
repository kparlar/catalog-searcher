package com.kparlar.catalogsearcher.controller.app;

import com.kparlar.catalogsearcher.exception.CatalogSearcherBadRequestException;
import com.kparlar.catalogsearcher.exception.CatalogSearcherException;
import com.kparlar.catalogsearcher.model.dto.SearchResponseDto;
import com.kparlar.catalogsearcher.services.SearchService;
import com.kparlar.catalogsearcher.util.CatalogSearcherConstants;
import com.kparlar.catalogsearcher.util.CatalogSearcherTestConstants;
import com.kparlar.catalogsearcher.util.provider.SearchResponseDtoProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

public class SearchControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private SearchService searchService;

    private SearchController searchController;
    private List<SearchResponseDto>  searchResponseDtos;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.searchController = new SearchController(this.searchService);

        SearchResponseDtoProvider searchResponseDtoProvider = new SearchResponseDtoProvider();
        searchResponseDtos = searchResponseDtoProvider.createSearchResponseDtosForAlbum();


    }

    @Test
    public void getSearchGivenValidDataThenReturnSearchResponseDtos() throws CatalogSearcherException {

        Mockito.when(this.searchService.getSearch(eq(CatalogSearcherConstants.SEARCH_TERM_ALBUM),eq( CatalogSearcherTestConstants.DUMMY_SEARCH_TERM))).thenReturn(searchResponseDtos);
        ResponseEntity<List<SearchResponseDto>> result = searchController.getSearch(CatalogSearcherConstants.SEARCH_TERM_ALBUM, CatalogSearcherTestConstants.DUMMY_SEARCH_TERM);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(result.getBody().size(), 1);
        assertEquals(result.getBody().get(0).getTitle(), searchResponseDtos.get(0).getTitle());
        assertEquals(result.getBody().get(0).getArtists()[0], searchResponseDtos.get(0).getArtists()[0]);
    }
    @Test
    public void getSearchGivenValidNotValidSearchTermWhenSearchServiceThrowsExceptionThenThrowCatalogSearcherException() throws CatalogSearcherException {
        this.thrown.expect(CatalogSearcherException.class);
        Mockito.doThrow(CatalogSearcherException.class).when(this.searchService).getSearch(eq(CatalogSearcherConstants.SEARCH_TERM_ALBUM),eq( CatalogSearcherTestConstants.DUMMY_SEARCH_TERM));

        searchController.getSearch(CatalogSearcherConstants.SEARCH_TERM_ALBUM, CatalogSearcherTestConstants.DUMMY_SEARCH_TERM);

    }
}
