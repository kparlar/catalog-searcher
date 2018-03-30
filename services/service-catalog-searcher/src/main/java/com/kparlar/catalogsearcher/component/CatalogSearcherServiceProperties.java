package com.kparlar.catalogsearcher.component;

import com.kparlar.catalogsearcher.component.util.CatalogSearcherService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "catalog-searcher.service")
public class CatalogSearcherServiceProperties {


    private CatalogSearcherService itunesService;
    private CatalogSearcherService googleService;

    public CatalogSearcherService getItunesService() {
        return itunesService;
    }

    public void setItunesService(CatalogSearcherService itunesService) {
        this.itunesService = itunesService;
    }

    public CatalogSearcherService getGoogleService() {
        return googleService;
    }

    public void setGoogleService(CatalogSearcherService googleService) {
        this.googleService = googleService;
    }
}
