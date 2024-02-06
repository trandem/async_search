package dem.search.service.enrichment;

import dem.search.service.dto.RequestProductContext;
import dem.search.service.dto.EnrichmentContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BrandEnrichment implements RequestEnrichment {

    @Override
    public Mono<EnrichmentContext> enrich(RequestProductContext context) {
        var brandEnrich = new EnrichmentContext();
        brandEnrich.setBrandIds(context.getBrandIds());
        return Mono.just(brandEnrich);
    }
}
