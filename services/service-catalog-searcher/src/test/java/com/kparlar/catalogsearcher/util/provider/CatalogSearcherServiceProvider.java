package com.kparlar.catalogsearcher.util.provider;

import com.kparlar.catalogsearcher.services.util.CatalogSearcherService;
import com.kparlar.catalogsearcher.util.CatalogSearcherTestConstants;

public class CatalogSearcherServiceProvider {

    public CatalogSearcherService createItunesService(){
        CatalogSearcherService itunesService = new CatalogSearcherService();
        itunesService.setBaseUrl(CatalogSearcherTestConstants.DUMMY_URL_ITUNES);
        return itunesService;
    }
    public CatalogSearcherService createGoogleService(){
        CatalogSearcherService googleService = new CatalogSearcherService();
        googleService.setBaseUrl(CatalogSearcherTestConstants.DUMMY_URL_GOOGLE);
        return googleService;
    }
}
