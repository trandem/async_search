package dem.search.service;

import dem.search.entity.SearchProductEntity;
import dem.search.service.dto.RankingContext;
import dem.search.service.dto.RequestProductContext;
import dem.search.service.enrichment.RequestEnrichment;
import dem.search.service.ranking.ProductRanking;
import dem.search.service.search.SearchProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchProductManager implements SearchProductFlow {
    private final RequestEnrichment parallelEnrichment;
    private final SearchProductService elasticSearchQuery;
    private final ProductRanking parallelRanking;


    @Override
    public Mono<List<SearchProductEntity>> findProduct(RequestProductContext requestProductContext) {

        return parallelEnrichment.enrich(requestProductContext)
                .flatMap(elasticSearchQuery::queryProductWithCondition)
                .flatMap(productEntities -> {
                    var rankContext = new RankingContext(productEntities, 0L);
                    return parallelRanking.sortProduct(rankContext)
                            .map(rankScore -> {
                                var outputProduct = new ArrayList<SearchProductEntity>();
                                for (var entity : productEntities) {
                                    var score = rankScore.getRankInfos().get(entity.getId());
                                    entity.setRankingScore(score.getScore());

                                    outputProduct.add(entity);
                                }
                                outputProduct.sort((o1, o2) -> o2.getRankingScore().intValue() - o1.getRankingScore().intValue());
                                return outputProduct;
                            });
                }).map(searchProductEntities -> {
                    var pagingResponses = new ArrayList<SearchProductEntity>();
                    var limit = requestProductContext.getFrom() + requestProductContext.getSize();

                    for (var index = requestProductContext.getFrom(); index < limit; index++) {
                        pagingResponses.add(searchProductEntities.get(index));
                    }

                    return pagingResponses;
                });
    }
}
