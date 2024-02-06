package dem.search.service.search.builder;

import dem.search.service.dto.EnrichmentContext;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

public class CategoryBuilder implements EsQueryBuilder {
    private static final String FIELD_NAME = "categories.category_id";

    @Override
    public Optional<QueryBuilder> query(EnrichmentContext enrichmentContext) {
        return Optional.ofNullable(enrichmentContext.getCategories())
                .filter(cates -> !CollectionUtils.isEmpty(cates))
                .map(values -> QueryBuilders.termsQuery(FIELD_NAME, values));
    }


}
