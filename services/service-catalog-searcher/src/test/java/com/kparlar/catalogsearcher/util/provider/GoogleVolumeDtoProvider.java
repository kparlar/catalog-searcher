package com.kparlar.catalogsearcher.util.provider;

import com.kparlar.catalogsearcher.model.dto.GoogleVolumeDto;
import com.kparlar.catalogsearcher.model.dto.GoogleVolumeInfoDto;
import com.kparlar.catalogsearcher.util.CatalogSearcherTestConstants;

import java.util.ArrayList;
import java.util.List;

public class GoogleVolumeDtoProvider {

    public GoogleVolumeDto createGoogleVolumeDto(){
        GoogleVolumeDto googleVolumeDto = new GoogleVolumeDto();
        GoogleVolumeInfoDtoProvider googleVolumeInfoDtoProvider = new GoogleVolumeInfoDtoProvider();
        GoogleVolumeInfoDto googleVolumeInfoDto = googleVolumeInfoDtoProvider.createGoogleVolumeInfoDto();
        googleVolumeDto.setVolumeInfo(googleVolumeInfoDto);
        return googleVolumeDto;
    }

    public List<GoogleVolumeDto> createGoogleVolumeDtos(){
        List<GoogleVolumeDto> googleVolumeDtos = new ArrayList<>();
        GoogleVolumeDto googleVolumeDto = createGoogleVolumeDto();
        googleVolumeDtos.add(googleVolumeDto);
        return googleVolumeDtos;
    }
}
