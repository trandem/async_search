package dem.search.service.search;

import dem.search.entity.SearchProductEntity;
import dem.search.service.dto.EnrichmentContext;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SearchProductService {
    Mono<List<SearchProductEntity>> queryProductWithCondition(EnrichmentContext enrichmentContext);
}
