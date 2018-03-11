package com.kparlar.catalogsearcher.services;

import com.kparlar.catalogsearcher.exception.CatalogSearcherBadRequestException;
import com.kparlar.catalogsearcher.exception.CatalogSearcherException;
import com.kparlar.catalogsearcher.model.dto.SearchResponseDto;
import com.kparlar.catalogsearcher.util.CatalogSearcherConstants;
import com.kparlar.catalogsearcher.util.MessageCodeConstants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class SearchService {


    private ItunesService itunesService;
    private GoogleService googleService;

    public SearchService(ItunesService itunesService, GoogleService googleService){
        this.itunesService = itunesService;
        this.googleService = googleService;
    }


    public List<SearchResponseDto> getSearch(String searchType, String searchTerm) throws CatalogSearcherException{
        List<SearchResponseDto> searchResponseDtos;
        switch (searchType){
            case CatalogSearcherConstants.SEARCH_TERM_ALL:
                searchResponseDtos = searchAll(searchTerm);
                break;
            case CatalogSearcherConstants.SEARCH_TERM_ALBUM:
                searchResponseDtos = getSearchResponseDtosFromCompletableFuture(this.itunesService.getSearch(searchTerm), MessageCodeConstants.ITUNES_API_ERROR_EXCEPTION_MESSAGE, MessageCodeConstants.ITUNES_API_ERROR_EXCEPTION_CODE);
                break;
            case CatalogSearcherConstants.SEARCH_TERM_BOOK:
                searchResponseDtos = getSearchResponseDtosFromCompletableFuture(this.googleService.getSearch(searchTerm), MessageCodeConstants.GOOGLE_BOOK_API_ERROR_EXCEPTION_MESSAGE, MessageCodeConstants.GOOGLE_BOOK_API_ERROR_EXCEPTION_CODE );
                break;
            default:
                throw new CatalogSearcherBadRequestException(MessageCodeConstants.NOT_VALID_SEARCH_TYPE_EXCEPTION_MESSAGE, MessageCodeConstants.NOT_VALID_SEARCH_TYPE_EXCEPTION_CODE, true);
        }
        return searchResponseDtos.stream().sorted((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle())).collect(Collectors.toList());
    }

    private List<SearchResponseDto> getSearchResponseDtosFromCompletableFuture(Future<List<SearchResponseDto>> searchResponseDtosCF, String errorMessage, String errorCode) throws CatalogSearcherException {

        List<SearchResponseDto> searchResponseDtos;
        try {
            searchResponseDtos = searchResponseDtosCF.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new CatalogSearcherException(errorMessage, errorCode, true);
        }
        return searchResponseDtos;
    }

    private List<SearchResponseDto> searchAll(String searchTerm) throws CatalogSearcherException {
        Future<List<SearchResponseDto>> searchItunesResponseDtosCF = this.itunesService.getSearch(searchTerm);
        Future<List<SearchResponseDto>> searchGoogleResponseDtosCF = this.googleService.getSearch(searchTerm);
        List<SearchResponseDto> searchItunesResponseDtos = getSearchResponseDtosFromCompletableFuture(searchItunesResponseDtosCF, MessageCodeConstants.ITUNES_API_ERROR_EXCEPTION_MESSAGE, MessageCodeConstants.ITUNES_API_ERROR_EXCEPTION_CODE);
        List<SearchResponseDto> searchGoogleResponseDtos = getSearchResponseDtosFromCompletableFuture(searchGoogleResponseDtosCF, MessageCodeConstants.GOOGLE_BOOK_API_ERROR_EXCEPTION_MESSAGE, MessageCodeConstants.GOOGLE_BOOK_API_ERROR_EXCEPTION_CODE);
        searchItunesResponseDtos.addAll(searchGoogleResponseDtos);
        return searchItunesResponseDtos;
    }


}
