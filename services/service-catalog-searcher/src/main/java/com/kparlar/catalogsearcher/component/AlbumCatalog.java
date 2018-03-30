package com.kparlar.catalogsearcher.component;

import com.kparlar.catalogsearcher.model.dto.ItunesResponseDto;
import com.kparlar.catalogsearcher.model.dto.SearchResponseDto;
import com.kparlar.catalogsearcher.component.util.CatalogSearcherServiceCall;
import com.kparlar.catalogsearcher.util.CatalogSearcherConstants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AlbumCatalog implements Catalog{

    private CatalogSearcherServiceProperties catalogSearcherServiceProperties;
    private RestTemplate restTemplate;
    private Long maxResultSize;

    public AlbumCatalog(CatalogSearcherServiceProperties catalogSearcherServiceProperties, RestTemplate restTemplate, @Value("${catalog-searcher.search.param.max-result-size}")Long maxResultSize){
        this.catalogSearcherServiceProperties = catalogSearcherServiceProperties;
        this.restTemplate = restTemplate;
        this.maxResultSize = maxResultSize;
    }

    @HystrixCommand(commandKey = "getSearchItunes", fallbackMethod = "reliable", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    }
    )
    public Future<List<SearchResponseDto>> getSearch(String searchTerm) {
        return new AsyncResult<List<SearchResponseDto>>() {
            @Override
            public List<SearchResponseDto>   invoke() {
                StringBuilder searchQuery = new StringBuilder();
                searchQuery.append("?")
                        .append("lang").append("=").append("en_us").append("&")
                        .append("term").append("=").append(searchTerm).append("&")
                        .append("attribute-type").append("=").append("music").append("&").append("limit").append("=").append(maxResultSize);
                CatalogSearcherServiceCall catalogSearcherServiceCall = new CatalogSearcherServiceCall(catalogSearcherServiceProperties.getItunesService(),
                        searchQuery, restTemplate);


                ItunesResponseDto itunesResponseDto  = catalogSearcherServiceCall.getForObject(ItunesResponseDto.class);

                if (itunesResponseDto.getResultCount() > 0)
                    return itunesResponseDto.getResults().stream().map(itunesSongDto ->
                            new SearchResponseDto(new String[]{itunesSongDto.getArtistName()!=null?itunesSongDto.getArtistName():""}, itunesSongDto.getTrackName()!=null?itunesSongDto.getTrackName():"", CatalogSearcherConstants.SEARCH_TERM_ALBUM)).limit(maxResultSize).collect(Collectors.toList());
                else
                    return new ArrayList<>();

            }
        };
    }
    @HystrixCommand(commandKey = "reliableItunes")
    public Future<List<SearchResponseDto>> reliable(String searchTerm) {
        return new AsyncResult<List<SearchResponseDto>>() {
            @Override
            public List<SearchResponseDto> invoke() {
                return new ArrayList<>();
            }
        };
    }




}
