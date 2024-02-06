package dem.search.service.search.builder;

import dem.search.service.dto.EnrichmentContext;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Optional;

public class NameQueryBuilder implements EsQueryBuilder {
    private static final String FIELD_NAME = "name";

    @Override
    public Optional<QueryBuilder> query(EnrichmentContext enrichmentContext) {
        return Optional.ofNullable(enrichmentContext.getQuery())
                .map(name -> QueryBuilders.matchQuery(FIELD_NAME, name));
    }
}
