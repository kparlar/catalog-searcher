package com.kparlar.catalogsearcher.exception;

import org.springframework.http.HttpStatus;

public class CatalogSearcherException extends Exception {

    private final HttpStatus status;
    private final String errorMessage;
    private final String errorCode;
    private final boolean showMessage;

    public CatalogSearcherException(String errorMessage, String errorCode, boolean showMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.showMessage = showMessage;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;// Default http status when not given
    }
    public CatalogSearcherException(String errorMessage, String errorCode, HttpStatus httpStatus, boolean showMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.showMessage = showMessage;
        this.status = httpStatus;// Default http status when not given
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public boolean isShowMessage() {
        return showMessage;
    }
}
