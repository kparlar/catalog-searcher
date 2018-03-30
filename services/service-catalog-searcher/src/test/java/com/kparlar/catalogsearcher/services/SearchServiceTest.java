package com.kparlar.catalogsearcher.services;

import com.kparlar.catalogsearcher.component.BookCatalog;
import com.kparlar.catalogsearcher.component.AlbumCatalog;
import com.kparlar.catalogsearcher.component.Catalog;
import com.kparlar.catalogsearcher.exception.CatalogSearcherBadRequestException;
import com.kparlar.catalogsearcher.exception.CatalogSearcherException;
import com.kparlar.catalogsearcher.model.dto.SearchResponseDto;
import com.kparlar.catalogsearcher.util.CatalogSearcherConstants;
import com.kparlar.catalogsearcher.util.CatalogSearcherTestConstants;
import com.kparlar.catalogsearcher.util.HystrixAsync;
import com.kparlar.catalogsearcher.util.provider.SearchResponseDtoProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
public class SearchServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private Catalog albumCatalog;
    @Mock
    private Catalog bookCatalog;

    private SearchService searchService;
    private HystrixAsync hystrixAsync;

    private List<SearchResponseDto> searchResponseDtosForAlbum;
    private List<SearchResponseDto> searchResponseDtosForBook;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.searchService = new SearchService(this.albumCatalog, this.bookCatalog);
        hystrixAsync = new HystrixAsync();

        SearchResponseDtoProvider searchResponseDtoProvider = new SearchResponseDtoProvider();
        searchResponseDtosForAlbum = searchResponseDtoProvider.createSearchResponseDtosForAlbum();
        searchResponseDtosForBook = searchResponseDtoProvider.createSearchResponseDtosForBook();
    }

    @Test
    public void getSearchGivenValidInputWhenCaseAllThenReturnOneBookAndOneAlbumInOrderWay() throws CatalogSearcherException {
        Mockito.when(this.albumCatalog.getSearch(eq(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM))).thenReturn(hystrixAsync.getFuture(searchResponseDtosForAlbum));
        Mockito.when(this.bookCatalog.getSearch(eq(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM))).thenReturn(hystrixAsync.getFuture(searchResponseDtosForBook));
        List<SearchResponseDto> result = this.searchService.getSearch(CatalogSearcherConstants.SEARCH_TERM_ALL, CatalogSearcherTestConstants.DUMMY_SEARCH_TERM);
        assertEquals(result.size(), 2);
        assertEquals(result.get(0).getArtists()[0], searchResponseDtosForAlbum.get(0).getArtists()[0]);
        assertEquals(result.get(0).getTitle(), searchResponseDtosForAlbum.get(0).getTitle());
        assertEquals(result.get(1).getArtists()[0], searchResponseDtosForBook.get(0).getArtists()[0]);
        assertEquals(result.get(1).getTitle(), searchResponseDtosForBook.get(0).getTitle());
    }
    @Test
    public void getSearchGivenValidInputWhenCaseAlbumThenReturnOneAlbum() throws CatalogSearcherException {
        Mockito.when(this.albumCatalog.getSearch(eq(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM))).thenReturn(hystrixAsync.getFuture(searchResponseDtosForAlbum));
        List<SearchResponseDto> result = this.searchService.getSearch(CatalogSearcherConstants.SEARCH_TERM_ALBUM, CatalogSearcherTestConstants.DUMMY_SEARCH_TERM);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getArtists()[0], searchResponseDtosForAlbum.get(0).getArtists()[0]);
        assertEquals(result.get(0).getTitle(), searchResponseDtosForAlbum.get(0).getTitle());
    }
    @Test
    public void getSearchGivenValidInputWhenCaseBookThenReturnOneBook() throws CatalogSearcherException {
        Mockito.when(this.bookCatalog.getSearch(eq(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM))).thenReturn(hystrixAsync.getFuture(searchResponseDtosForBook));
        List<SearchResponseDto> result = this.searchService.getSearch(CatalogSearcherConstants.SEARCH_TERM_BOOK, CatalogSearcherTestConstants.DUMMY_SEARCH_TERM);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getArtists()[0], searchResponseDtosForBook.get(0).getArtists()[0]);
        assertEquals(result.get(0).getTitle(), searchResponseDtosForBook.get(0).getTitle());
    }
    @Test
    public void getSearchGivenValidInputWhenExceptionInFutureThenThrowInterruptedException() throws CatalogSearcherException {

        this.thrown.expect(CatalogSearcherException.class);
        Mockito.when(this.bookCatalog.getSearch(eq(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM))).thenReturn(hystrixAsync.getFutureForInterruptedException());
        this.searchService.getSearch(CatalogSearcherConstants.SEARCH_TERM_BOOK, CatalogSearcherTestConstants.DUMMY_SEARCH_TERM);
    }

    @Test
    public void getSearchGivenValidInputWhenExceptionInFutureThenThrowExecutionException() throws CatalogSearcherException {

        this.thrown.expect(CatalogSearcherException.class);
        Mockito.when(this.bookCatalog.getSearch(eq(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM))).thenReturn(hystrixAsync.getFutureForExecutionException());
        this.searchService.getSearch(CatalogSearcherConstants.SEARCH_TERM_BOOK, CatalogSearcherTestConstants.DUMMY_SEARCH_TERM);
    }

    @Test
    public void getSearchGivenNotValidSearchTermThenThrowCatalogSearcherBadRequestException() throws CatalogSearcherException {
        this.thrown.expect(CatalogSearcherBadRequestException.class);
        this.searchService.getSearch(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM, CatalogSearcherTestConstants.DUMMY_SEARCH_TERM);
    }
}
