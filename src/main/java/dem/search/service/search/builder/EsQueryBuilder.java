package dem.search.service.search.builder;

import dem.search.service.dto.EnrichmentContext;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Optional;

public interface EsQueryBuilder {
    Optional<QueryBuilder> query(EnrichmentContext enrichmentContext);
}
