package com.kparlar.catalogsearcher.controller.app;


import com.codahale.metrics.annotation.Timed;
import com.kparlar.catalogsearcher.exception.CatalogSearcherException;
import com.kparlar.catalogsearcher.model.dto.SearchResponseDto;
import com.kparlar.catalogsearcher.services.SearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/app/catalog-searcher/v1/search")
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }
    @Timed(name = "kp-search-service")
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ApiOperation(value = "Search all , album or book (has to be lower-case according to search type",
            notes = "Each search will be done according to Search Type. Search Types are 'album', 'book' or 'all'. No other search type is accepted and return error. " +
                    "Search is limitted to 5 data. Results are sorted in alphabetically ascending order. ")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Successfully searched and return result."),
                    @ApiResponse(code = 400, message = "Search Type has to be all, album or book (has to be lower-case) otherwise this exception will be thrown"),
                    @ApiResponse(code = 500, message = "Internal Server Error, thrown by GlobalControllerException") })
    public ResponseEntity<List<SearchResponseDto>> getSearch(@RequestParam(value="type", required = true) String type, @RequestParam(value="term", required = true) String term) throws CatalogSearcherException {
        return new ResponseEntity<>(this.searchService.getSearch(type, term), HttpStatus.OK);
    }


}
