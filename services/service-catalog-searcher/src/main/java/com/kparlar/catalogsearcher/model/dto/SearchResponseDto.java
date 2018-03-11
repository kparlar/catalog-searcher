package com.kparlar.catalogsearcher.model.dto;

public class SearchResponseDto {


    private String[] artists;
    private String title;
    private String  type;

    public SearchResponseDto(String[] artists, String title, String type){
        this.artists = artists;
        this.title = title;
        this.type = type;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getArtists() {
        return artists;
    }

    public void setArtists(String[] artists) {
        this.artists = artists;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
