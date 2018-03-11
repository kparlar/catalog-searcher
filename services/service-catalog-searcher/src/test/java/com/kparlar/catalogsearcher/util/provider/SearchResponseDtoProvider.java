package com.kparlar.catalogsearcher.util.provider;

import com.kparlar.catalogsearcher.model.dto.SearchResponseDto;
import com.kparlar.catalogsearcher.util.CatalogSearcherConstants;
import com.kparlar.catalogsearcher.util.CatalogSearcherTestConstants;

import java.util.ArrayList;
import java.util.List;

public class SearchResponseDtoProvider {

    public SearchResponseDto createSearchResponseDtoForAlbumWithTitleFirst(){
        String[] authors = {CatalogSearcherTestConstants.DUMMY_ARTIST_NAME_FIRST};
        SearchResponseDto searchResponseDto = new SearchResponseDto(authors, CatalogSearcherTestConstants.DUMMY_TITLE_FIRST, CatalogSearcherConstants.SEARCH_TERM_ALBUM);
        return searchResponseDto;
    }
    public SearchResponseDto createSearchResponseDtoForBookWithTitleSecond(){
        String[] authors = {CatalogSearcherTestConstants.DUMMY_ARTIST_NAME_SECOND};
        SearchResponseDto searchResponseDto = new SearchResponseDto(authors, CatalogSearcherTestConstants.DUMMY_TITLE_SECOND, CatalogSearcherConstants.SEARCH_TERM_BOOK);
        return searchResponseDto;
    }
    public List<SearchResponseDto> createSearchResponseDtosForAlbum(){
        List<SearchResponseDto> searchResponseDtos = new ArrayList<>();
        SearchResponseDto searchResponseDto = createSearchResponseDtoForAlbumWithTitleFirst();
        searchResponseDtos.add(searchResponseDto);
        return searchResponseDtos;
    }
    public List<SearchResponseDto> createSearchResponseDtosForBook(){
        List<SearchResponseDto> searchResponseDtos = new ArrayList<>();
        SearchResponseDto searchResponseDto = createSearchResponseDtoForBookWithTitleSecond();
        searchResponseDtos.add(searchResponseDto);
        return searchResponseDtos;
    }
}
