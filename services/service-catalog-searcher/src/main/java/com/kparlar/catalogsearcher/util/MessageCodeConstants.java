package com.kparlar.catalogsearcher.util;

public final class MessageCodeConstants {
    public static final String REST_CLIENT_EXCEPTION_MESSAGE = "org.springframework.web.client.RestClientException: Code: %s: id %s";
    public static final String REST_CLIENT_EXCEPTION_CODE = "EX01";

    public static final String NOT_FOUND_EXCEPTION_MESSAGE = "Data not found. id :%s";
    public static final String NOT_FOUND_EXCEPTION_CODE = "EX02";

    public static final String NOT_VALID_SEARCH_TYPE_EXCEPTION_MESSAGE = "Search Type has to be ALBUM or BOOK.";
    public static final String NOT_VALID_SEARCH_TYPE_EXCEPTION_CODE = "EX03";

    public static final String GOOGLE_BOOK_API_ERROR_EXCEPTION_MESSAGE = "Error occured while accessing Google Books API";
    public static final String GOOGLE_BOOK_API_ERROR_EXCEPTION_CODE = "EX04";

    public static final String ITUNES_API_ERROR_EXCEPTION_MESSAGE = "Error occured while accessing Itunes API";
    public static final String ITUNES_API_ERROR_EXCEPTION_CODE = "EX05";

    public static final String API_ERROR_EXCEPTION_MESSAGE = "Error occured while accessing Google or Itunes API";
    public static final String API_ERROR_EXCEPTION_CODE = "EX06";

    private MessageCodeConstants(){

    }

}
