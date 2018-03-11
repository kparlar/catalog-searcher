package com.kparlar.catalogsearcher.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ItunesResponseDto {

    private long resultCount;
    private List<ItunesSongDto> results;

    public ItunesResponseDto(){
        this.resultCount = 0;
        this.results = new ArrayList<>();
    }

    public long getResultCount() {
        return resultCount;
    }

    public void setResultCount(long resultCount) {
        this.resultCount = resultCount;
    }

    public List<ItunesSongDto> getResults() {
        return results;
    }

    public void setResults(List<ItunesSongDto> results) {
        this.results = results;
    }
}
