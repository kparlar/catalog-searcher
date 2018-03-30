package com.kparlar.catalogsearcher.component;

import com.kparlar.catalogsearcher.model.dto.SearchResponseDto;
import java.util.List;
import java.util.concurrent.Future;

public interface Catalog {


  public Future<List<SearchResponseDto>> getSearch(String searchTerm);
  public Future<List<SearchResponseDto>> reliable(String searchTerm);

}
