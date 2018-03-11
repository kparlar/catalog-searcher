package com.kparlar.catalogsearcher.services;


import com.kparlar.catalogsearcher.model.dto.ItunesResponseDto;
import com.kparlar.catalogsearcher.model.dto.SearchResponseDto;
import com.kparlar.catalogsearcher.services.util.CatalogSearcherService;
import com.kparlar.catalogsearcher.services.util.CatalogSearcherServiceProperties;
import com.kparlar.catalogsearcher.util.CatalogSearcherTestConstants;
import com.kparlar.catalogsearcher.util.HystrixAsync;
import com.kparlar.catalogsearcher.util.provider.CatalogSearcherServiceProvider;
import com.kparlar.catalogsearcher.util.provider.ItunesResponseDtoProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

public class ItunesServiceTest {

    @Mock
    private CatalogSearcherServiceProperties catalogSearcherServiceProperties;
    @Mock
    private RestTemplate restTemplate;

    private ItunesService itunesService;
    private CatalogSearcherService catalogSearcherServiceItunes;
    private ItunesResponseDto itunesResponseDto;
    private HystrixAsync hystrixAsync;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        CatalogSearcherServiceProvider catalogSearcherServiceProvider = new CatalogSearcherServiceProvider();
        catalogSearcherServiceItunes = catalogSearcherServiceProvider.createItunesService();

        itunesService = new ItunesService(this.catalogSearcherServiceProperties, this.restTemplate, CatalogSearcherTestConstants.MAX_RESULT_SIZE);

        ItunesResponseDtoProvider itunesResponseDtoProvider = new ItunesResponseDtoProvider();
        itunesResponseDto = itunesResponseDtoProvider.createItunesResponseDto();
        hystrixAsync = new HystrixAsync();

    }

    @Test
    public void getSearchItunesGivenValidSearchTermWhenServiceDownThenEmptyArray() throws ExecutionException, InterruptedException {
        Mockito.when(restTemplate.getForObject(anyString(), eq(ItunesResponseDto.class))).thenReturn(new ItunesResponseDto());
        Mockito.when(catalogSearcherServiceProperties.getItunesService()).thenReturn(this.catalogSearcherServiceItunes);
        assertEquals( hystrixAsync.resolve(itunesService.getSearch(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM)).size(), 0);
    }
    @Test
    public void getSearchItunesGivenValidSearchTermWhenServiceSuccessfullyRespondThenSearchResponseDtos() throws ExecutionException, InterruptedException {
        Mockito.when(restTemplate.getForObject(anyString(), eq(ItunesResponseDto.class))).thenReturn(itunesResponseDto);
        Mockito.when(catalogSearcherServiceProperties.getItunesService()).thenReturn(this.catalogSearcherServiceItunes);
        List<SearchResponseDto> result = hystrixAsync.resolve(itunesService.getSearch(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM));
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getTitle(), itunesResponseDto.getResults().get(0).getTrackName());
        assertEquals(result.get(0).getArtists()[0], itunesResponseDto.getResults().get(0).getArtistName());
    }
    @Test
    public void reliableItunesGivenValidSearchTermThenEmptyArray() throws ExecutionException, InterruptedException {
        assertEquals( hystrixAsync.resolve(itunesService.reliable(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM)).size(), 0);
    }

}
