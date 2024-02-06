package dem.search.service.enrichment;

import dem.search.service.dto.RequestProductContext;
import dem.search.service.dto.EnrichmentContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.Normalizer;

@Service
public class NormalizeQueryEnrichment implements RequestEnrichment {

    @Override
    public Mono<EnrichmentContext> enrich(RequestProductContext context) {
        //simple normalize text
        var query = Normalizer.normalize(context.getQuery(), Normalizer.Form.NFD).replaceAll(" +", " ");
        var normalizeText = new EnrichmentContext();
        normalizeText.setQuery(query);
        return Mono.just(normalizeText);
    }
}
