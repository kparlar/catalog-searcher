package com.kparlar.catalogsearcher.model.dto;

public class GoogleVolumeDto {
    private String kind;
    private String id;
    private String etag;
    private String selfLink;
    GoogleVolumeInfoDto volumeInfo;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public GoogleVolumeInfoDto getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(GoogleVolumeInfoDto volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}
