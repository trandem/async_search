package dem.search.service.ranking;

import dem.search.service.dto.RankingContext;
import dem.search.entity.RankingResponse;
import reactor.core.publisher.Mono;

public interface ProductRanking {
    Mono<RankingResponse> sortProduct(RankingContext rankingContext);
}
