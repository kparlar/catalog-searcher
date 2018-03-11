package com.kparlar.catalogsearcher.util.provider;

import com.kparlar.catalogsearcher.model.dto.ItunesResponseDto;
import com.kparlar.catalogsearcher.model.dto.ItunesSongDto;

import java.util.List;

public class ItunesResponseDtoProvider {

    public ItunesResponseDto createItunesResponseDto(){
        ItunesResponseDto itunesResponseDto = new ItunesResponseDto();
        itunesResponseDto.setResultCount(1);
        ItunesSongDtoProvider itunesSongDtoProvider = new ItunesSongDtoProvider();
        List<ItunesSongDto> itunesSongDtos =  itunesSongDtoProvider.createItunesSongDtos();
        itunesResponseDto.setResults(itunesSongDtos);
        return itunesResponseDto;
    }
}
