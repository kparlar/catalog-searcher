package com.kparlar.catalogsearcher.controller;

import com.kparlar.catalogsearcher.exception.CatalogSearcherException;
import com.kparlar.catalogsearcher.exception.ExceptionMessage;
import com.kparlar.catalogsearcher.util.CatalogSearcherTestConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import static org.junit.Assert.assertEquals;

public class GlobalControllerExceptionHandlerTest {

    private GlobalControllerExceptionHandler globalControllerExceptionHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        globalControllerExceptionHandler = new GlobalControllerExceptionHandler();

    }

    @Test
    public void handleExceptionGivenExceptionThenErrorStatus() {
        ResponseEntity<ExceptionMessage> response =
                globalControllerExceptionHandler.handleException(new Exception(
                        CatalogSearcherTestConstants.DUMMY_TEXT));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void handleRestClientExceptionGivenExceptionThenErrorStatus() {
        ResponseEntity<ExceptionMessage> response =
                globalControllerExceptionHandler.handleRestClientException(new RestClientException(
                        CatalogSearcherTestConstants.DUMMY_TEXT));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void handleDtExceptionGivenExceptionThenErrorStatus() {
        ResponseEntity<ExceptionMessage> response =
                globalControllerExceptionHandler.handleCatalogSearcherException(new CatalogSearcherException(CatalogSearcherTestConstants.DUMMY_TEXT, CatalogSearcherTestConstants.DUMMY_TEXT, true));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
