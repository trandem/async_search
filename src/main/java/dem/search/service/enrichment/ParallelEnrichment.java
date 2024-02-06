package dem.search.service.enrichment;

import dem.search.service.dto.RequestProductContext;
import dem.search.service.dto.EnrichmentContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ParallelEnrichment implements RequestEnrichment {

    private final RequestEnrichment categoryEnrichment;
    private final RequestEnrichment brandEnrichment;
    private final RequestEnrichment normalizeQueryEnrichment;
    private final RequestEnrichment sortEnrichment;

    @Override
    public Mono<EnrichmentContext> enrich(RequestProductContext context) {
        var cateMono = categoryEnrichment.enrich(context);
        var brandMono = brandEnrichment.enrich(context);
        var textMono = normalizeQueryEnrichment.enrich(context);
        var sortMono = sortEnrichment.enrich(context);

        return Mono.zip(List.of(cateMono, brandMono, textMono, sortMono), new Function<Object[], EnrichmentContext>() {
            @Override
            public EnrichmentContext apply(Object[] objects) {
                var cateEnrichment = (EnrichmentContext) objects[0];
                var brandEnrichment = (EnrichmentContext) objects[1];
                var textEnrichment = (EnrichmentContext) objects[2];
                var sortEnrichment = (EnrichmentContext) objects[3];
                return EnrichmentContext.builder()
                        .categories(cateEnrichment.getCategories())
                        .brandIds(brandEnrichment.getBrandIds())
                        .query(textEnrichment.getQuery())
                        .sortBy(sortEnrichment.getSortBy())
                        .build();
            }
        });
    }
}
