package dem.search.service.enrichment;

import dem.search.service.dto.RequestProductContext;
import dem.search.service.dto.EnrichmentContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SortEnrichment implements RequestEnrichment {
    @Override
    public Mono<EnrichmentContext> enrich(RequestProductContext context) {
        var sortEnrichment = new EnrichmentContext();

        if (context.getSortBy() != null) {
            sortEnrichment.setSortBy(context.getSortBy());
        }

        return Mono.just(sortEnrichment);
    }
}
