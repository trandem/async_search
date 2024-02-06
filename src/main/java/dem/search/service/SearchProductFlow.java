package dem.search.service;

import dem.search.entity.SearchProductEntity;
import dem.search.service.dto.RequestProductContext;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SearchProductFlow {
    Mono<List<SearchProductEntity>> findProduct(RequestProductContext requestProductContext);
}
