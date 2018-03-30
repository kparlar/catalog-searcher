package com.kparlar.catalogsearcher.component;

import com.kparlar.catalogsearcher.model.dto.GoogleResponseDto;
import com.kparlar.catalogsearcher.model.dto.SearchResponseDto;
import com.kparlar.catalogsearcher.component.util.CatalogSearcherServiceCall;
import com.kparlar.catalogsearcher.util.CatalogSearcherConstants;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class BookCatalog implements Catalog{

    private CatalogSearcherServiceProperties catalogSearcherServiceProperties;
    private RestTemplate restTemplate;
    private Long maxResultSize;

    public BookCatalog(CatalogSearcherServiceProperties catalogSearcherServiceProperties, RestTemplate restTemplate,
                         @Value("${catalog-searcher.search.param.max-result-size}")Long maxResultSize){
        this.catalogSearcherServiceProperties = catalogSearcherServiceProperties;
        this.restTemplate = restTemplate;
        this.maxResultSize = maxResultSize;
    }
    @HystrixCommand(commandKey = "getSearchGoogle", fallbackMethod = "reliable", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    }
    )
    public Future<List<SearchResponseDto>> getSearch(String searchTerm){
        return new AsyncResult<List<SearchResponseDto>>() {
            @Override
            public List<SearchResponseDto>   invoke() {
                StringBuilder searchQuery = new StringBuilder();
                searchQuery.append("?")
                        .append("q").append("=").append(searchTerm).append("&")
                        .append("maxResults").append("=").append(maxResultSize).append("&")
                        .append("orderBy").append("=").append("newest");
                CatalogSearcherServiceCall catalogSearcherServiceCall = new CatalogSearcherServiceCall(catalogSearcherServiceProperties.getGoogleService(),
                        searchQuery, restTemplate);


                GoogleResponseDto googleResponseDto  = catalogSearcherServiceCall.getForObject(GoogleResponseDto.class);

                if(googleResponseDto.getTotalItems()>0)
                    return googleResponseDto.getItems().stream().map(googleVolumeDto -> new SearchResponseDto(googleVolumeDto.getVolumeInfo().getAuthors()!=null?
                            googleVolumeDto.getVolumeInfo().getAuthors().toArray(new String[0]):new String[0], googleVolumeDto.getVolumeInfo().getTitle()!=null?googleVolumeDto.getVolumeInfo().getTitle():"", CatalogSearcherConstants.SEARCH_TERM_BOOK)).limit(maxResultSize).collect(Collectors.toList());
                else
                    return new ArrayList<>();
            }
        };



    }

    @HystrixCommand(commandKey = "reliableGoogle")
    public Future<List<SearchResponseDto>>  reliable(String searchTerm) {
        return new AsyncResult<List<SearchResponseDto>>() {
            @Override
            public List<SearchResponseDto> invoke() {
                return new ArrayList<>();
            }
        };
    }


}
