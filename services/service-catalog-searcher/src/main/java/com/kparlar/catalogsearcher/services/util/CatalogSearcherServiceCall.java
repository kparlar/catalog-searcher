package com.kparlar.catalogsearcher.services.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatalogSearcherServiceCall {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private CatalogSearcherService catalogSearcherService;
    private StringBuilder serviceQueryCallBuilder;
    private RestTemplate restTemplate;

    public CatalogSearcherServiceCall(CatalogSearcherService catalogSearcherService, StringBuilder serviceQueryCallBuilder, RestTemplate restTemplate) {
       this.catalogSearcherService = catalogSearcherService;
       this.serviceQueryCallBuilder = serviceQueryCallBuilder;
       this.restTemplate = restTemplate;

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

    }

    public <T> T getForObject(Class<T> responseType ){
        return restTemplate.getForObject( getFullPathService(), responseType);
    }
    private String getFullPathService() {
        String debugMessage =  "Full Service Query : " + this.catalogSearcherService.getBaseUrl()
                + this.serviceQueryCallBuilder.toString();
        logger.debug(debugMessage);
        return this.catalogSearcherService.getBaseUrl() + this.serviceQueryCallBuilder.toString();
    }
}
