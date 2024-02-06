package dem.search.service.enrichment;

import dem.search.service.dto.RequestProductContext;
import dem.search.service.dto.EnrichmentContext;
import reactor.core.publisher.Mono;

public interface RequestEnrichment {
    Mono<EnrichmentContext> enrich(RequestProductContext context);
}
