package dem.search.service.ranking;

import dem.search.entity.RankingResponse;
import dem.search.entity.SearchProductEntity;
import dem.search.service.dto.RankingContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
/**
 * ranking product following hot, trending keyword. for example :iphone 15 although it is new product.
 * Use prefix tree for building and checking
 */
public class TrendingKeywordRanking implements ProductRanking {
    @Override
    public Mono<RankingResponse> sortProduct(RankingContext rankingContext) {
        var response = new RankingResponse();

        for (var entity : rankingContext.getProductEntities()) {
            var info = new RankingResponse.RankInfo(entity.getId(), 0.0);
            if (checkHotKeyWord(entity)) {
                info.setScore(10.0);
            }
            response.appendInfo(info);
        }

        return Mono.just(response);
    }


    private boolean checkHotKeyWord(SearchProductEntity searchProduct) {
        return true;
    }
}
