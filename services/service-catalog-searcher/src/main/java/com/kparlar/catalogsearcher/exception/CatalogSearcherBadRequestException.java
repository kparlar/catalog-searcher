package com.kparlar.catalogsearcher.exception;

import org.springframework.http.HttpStatus;

public class CatalogSearcherBadRequestException extends CatalogSearcherException {
    public CatalogSearcherBadRequestException(String errorMessage, String errorCode, boolean showMessage) {
        super(errorMessage, errorCode, HttpStatus.BAD_REQUEST, showMessage);
    }
}
