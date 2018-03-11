package com.kparlar.catalogsearcher.model.dto;

import java.util.ArrayList;
import java.util.List;

public class GoogleResponseDto {


    private String kind;
    private long totalItems;
    private List<GoogleVolumeDto> items;

    public GoogleResponseDto(){
        this.totalItems = 0;
        this.items = new ArrayList<>();
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public List<GoogleVolumeDto> getItems() {
        return items;
    }

    public void setItems(List<GoogleVolumeDto> items) {
        this.items = items;
    }
}
