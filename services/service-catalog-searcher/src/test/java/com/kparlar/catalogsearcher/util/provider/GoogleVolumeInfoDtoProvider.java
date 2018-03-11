package com.kparlar.catalogsearcher.util.provider;

import com.kparlar.catalogsearcher.model.dto.GoogleVolumeInfoDto;
import com.kparlar.catalogsearcher.util.CatalogSearcherTestConstants;

import java.util.ArrayList;
import java.util.List;

public class GoogleVolumeInfoDtoProvider {

    public GoogleVolumeInfoDto createGoogleVolumeInfoDto(){
        GoogleVolumeInfoDto googleVolumeInfoDto = new GoogleVolumeInfoDto();
        List<String> authors = new ArrayList();
        authors.add(CatalogSearcherTestConstants.DUMMY_AUTHOR);
        googleVolumeInfoDto.setAuthors(authors);
        googleVolumeInfoDto.setTitle(CatalogSearcherTestConstants.DUMMY_TITLE_FIRST);
        return googleVolumeInfoDto;
    }

    public List<GoogleVolumeInfoDto> createGoogleVolumeInfoDtos(){
        List<GoogleVolumeInfoDto> googleVolumeInfoDtos = new ArrayList<>();
        GoogleVolumeInfoDto googleVolumeInfoDto = createGoogleVolumeInfoDto();
        googleVolumeInfoDtos.add(googleVolumeInfoDto);
        return googleVolumeInfoDtos;
    }
}
