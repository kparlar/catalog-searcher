package com.kparlar.catalogsearcher.services;

import com.kparlar.catalogsearcher.model.dto.GoogleResponseDto;
import com.kparlar.catalogsearcher.model.dto.SearchResponseDto;
import com.kparlar.catalogsearcher.services.util.CatalogSearcherService;
import com.kparlar.catalogsearcher.services.util.CatalogSearcherServiceProperties;
import com.kparlar.catalogsearcher.util.CatalogSearcherTestConstants;
import com.kparlar.catalogsearcher.util.HystrixAsync;
import com.kparlar.catalogsearcher.util.provider.CatalogSearcherServiceProvider;
import com.kparlar.catalogsearcher.util.provider.GoogleResponseDtoProvider;
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

public class GoogleServiceTest {

    @Mock
    CatalogSearcherServiceProperties catalogSearcherServiceProperties;
    @Mock
    private RestTemplate restTemplate;

    private GoogleService googleService;
    private CatalogSearcherService catalogSearcherServiceGoogle;
    private GoogleResponseDto googleResponseDto;
    private HystrixAsync hystrixAsync;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        CatalogSearcherServiceProvider catalogSearcherServiceProvider = new CatalogSearcherServiceProvider();
        catalogSearcherServiceGoogle = catalogSearcherServiceProvider.createGoogleService();

        googleService = new GoogleService(this.catalogSearcherServiceProperties, this.restTemplate, CatalogSearcherTestConstants.MAX_RESULT_SIZE);

        GoogleResponseDtoProvider googleResponseDtoProvider = new GoogleResponseDtoProvider();
        googleResponseDto = googleResponseDtoProvider.createGoogleResponseDto();

        hystrixAsync = new HystrixAsync();
    }
    @Test
    public void getSearchGivenValidSearchTermWhenServiceDownThenEmptyArray() throws ExecutionException, InterruptedException {
        Mockito.when(restTemplate.getForObject(anyString(), eq(GoogleResponseDto.class))).thenReturn(new GoogleResponseDto());
        Mockito.when(catalogSearcherServiceProperties.getGoogleService()).thenReturn(this.catalogSearcherServiceGoogle);
        assertEquals( hystrixAsync.resolve(googleService.getSearch(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM)).size(), 0);
    }

    @Test
    public void getSearchGoogleGivenValidSearchTermWhenServiceSuccessfullyRespondThenSearchResponseDtos() throws ExecutionException, InterruptedException {
        Mockito.when(restTemplate.getForObject(anyString(), eq(GoogleResponseDto.class))).thenReturn(googleResponseDto);
        Mockito.when(catalogSearcherServiceProperties.getGoogleService()).thenReturn(this.catalogSearcherServiceGoogle);
        List<SearchResponseDto> result = hystrixAsync.resolve(googleService.getSearch(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM));
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getTitle(), googleResponseDto.getItems().get(0).getVolumeInfo().getTitle());
        assertEquals(result.get(0).getArtists()[0], googleResponseDto.getItems().get(0).getVolumeInfo().getAuthors().get(0));
    }
    @Test
    public void reliableGoogleGivenValidSearchTermThenEmptyArray() throws ExecutionException, InterruptedException {
        assertEquals( hystrixAsync.resolve(googleService.reliable(CatalogSearcherTestConstants.DUMMY_SEARCH_TERM)).size(), 0);
    }
}
