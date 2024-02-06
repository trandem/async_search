package dem.search.service.search;

import dem.search.entity.SearchProductEntity;
import dem.search.service.dto.EnrichmentContext;
import dem.search.service.search.builder.CategoryBuilder;
import dem.search.service.search.builder.EsQueryBuilder;
import dem.search.service.search.builder.NameQueryBuilder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Setter
public class ElasticSearchQuery implements SearchProductService {

    @Value("${elastic.index}")
    private String catalogProduct;
    private final ReactiveElasticsearchClient elasticsearchClient;
    @Value("${search.maximum.size}")
    private int maximumSize;

    private static List<EsQueryBuilder> FILTER = List.of(new CategoryBuilder(), new NameQueryBuilder());

    @Override
    public Mono<List<SearchProductEntity>> queryProductWithCondition(EnrichmentContext enrichmentContext) {
        var sourceBuilder = new SearchSourceBuilder()
                .fetchSource(new String[]{"name"}, new String[]{})
                .fetchSource(true)
                .from(0)
                .size(maximumSize);
        sourceBuilder.query(buildQuery(enrichmentContext));

        if (enrichmentContext.getSortBy() != null) {
            sourceBuilder.sort(convertSort(enrichmentContext.getSortBy()));
        } else {
            sourceBuilder.sort("_score", SortOrder.DESC);
        }


        var request = new SearchRequest(catalogProduct);
        request.source(sourceBuilder);

        return elasticsearchClient.searchForResponse(request)
                .map(searchResponse -> {
                    var size = searchResponse.getHits().getHits().length;
                    var rs = new ArrayList<SearchProductEntity>(size);
                    for (var hit : searchResponse.getHits()) {
                        var sourceAsMap = hit.getSourceAsMap();

                        var responseEntity = SearchProductEntity.builder()
                                .id(Long.parseLong(hit.getId()))
                                .name(sourceAsMap.get("name").toString())
                                .origin("unknown")
                                .build();
                        rs.add(responseEntity);
                    }

                    return rs;
                });

    }

    private BoolQueryBuilder buildQuery(EnrichmentContext enrichmentContext) {
        var queries = QueryBuilders.boolQuery();
        FILTER.forEach(f -> f.query(enrichmentContext).ifPresent(queries::filter));
        return queries;
    }

    private String convertSort(Integer sort) {
        switch (sort) {
            case 1:
                return "id";
            case 2:
                return "created_at";
            case 3:
                return "price";
            default:
                return "_score";
        }
    }
}
