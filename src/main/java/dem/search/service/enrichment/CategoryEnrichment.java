package dem.search.service.enrichment;

import dem.search.repo.CategoryRepo;
import dem.search.repo.dto.CategoryRequest;
import dem.search.service.dto.RequestProductContext;
import dem.search.service.dto.EnrichmentContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryEnrichment implements RequestEnrichment {
    private final CategoryRepo categoryRepo;

    @Override
    public Mono<EnrichmentContext> enrich(RequestProductContext context) {
        // customer want to filter by categories
        if (!CollectionUtils.isEmpty(context.getCategories())) {
            var categoryContext = new EnrichmentContext();
            categoryContext.setCategories(context.getCategories());
            return Mono.just(categoryContext);
        }

        var categoryRequest = new CategoryRequest();
        categoryRequest.setQuery(context.getQuery());
        return categoryRepo.predictCategories(categoryRequest).map(
                        categoryResponse -> {
                            var categoryContext = new EnrichmentContext();
                            categoryContext.setCategories(categoryResponse.getListCategory());
                            return categoryContext;
                        })
                .onErrorResume(throwable -> {
                    log.error("call category service error ", throwable);
                    return Mono.just(new EnrichmentContext());
                });
    }
}
