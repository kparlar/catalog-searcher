package com.kparlar.catalogsearcher.util.provider;

import com.kparlar.catalogsearcher.model.dto.ItunesSongDto;
import com.kparlar.catalogsearcher.util.CatalogSearcherTestConstants;

import java.util.ArrayList;
import java.util.List;

public class ItunesSongDtoProvider {

    public ItunesSongDto createItunesSongDto(){
        ItunesSongDto itunesSongDto = new ItunesSongDto();
        itunesSongDto.setArtistName(CatalogSearcherTestConstants.DUMMY_ARTIST_NAME_FIRST);
        itunesSongDto.setTrackName(CatalogSearcherTestConstants.DUMMY_TRACK_NAME);
        return itunesSongDto;
    }
    public List<ItunesSongDto> createItunesSongDtos(){
        List<ItunesSongDto> itunesSongDtos = new ArrayList<>();
        ItunesSongDto itunesSongDto = createItunesSongDto();
        itunesSongDtos.add(itunesSongDto);
        return itunesSongDtos;
    }
}
