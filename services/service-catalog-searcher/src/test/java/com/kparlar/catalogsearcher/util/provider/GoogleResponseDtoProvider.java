package com.kparlar.catalogsearcher.util.provider;

import com.kparlar.catalogsearcher.model.dto.GoogleResponseDto;
import com.kparlar.catalogsearcher.model.dto.GoogleVolumeDto;
import com.kparlar.catalogsearcher.util.CatalogSearcherTestConstants;

import java.util.List;

public class GoogleResponseDtoProvider {

    public GoogleResponseDto createGoogleResponseDto(){
        GoogleResponseDto googleResponseDto = new GoogleResponseDto();
        GoogleVolumeDtoProvider googleVolumeDtoProvider = new GoogleVolumeDtoProvider();
        List<GoogleVolumeDto> googleVolumeDtos = googleVolumeDtoProvider.createGoogleVolumeDtos();
        googleResponseDto.setItems(googleVolumeDtos);
        googleResponseDto.setKind(CatalogSearcherTestConstants.DUMMY_TEXT);
        googleResponseDto.setTotalItems(1);
        return googleResponseDto;
    }
}
