package dem.search.service.ranking;

import dem.search.entity.RankingResponse;
import dem.search.service.dto.RankingContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ParallelRanking implements ProductRanking {

    private final ProductRanking trendingKeywordRanking;
    private final ProductRanking fastDeliveryRaking;

    @Override
    public Mono<RankingResponse> sortProduct(RankingContext rankingContext) {
        var trending = trendingKeywordRanking.sortProduct(rankingContext);
        var delivery = fastDeliveryRaking.sortProduct(rankingContext);

        return Mono.zip(List.of(trending, delivery), new Function<Object[], RankingResponse>() {
            @Override
            public RankingResponse apply(Object[] objects) {
                var aggRanking = new RankingResponse();

                for (var object : objects) {
                    var rank = (RankingResponse) object;
                    for (var info : rank.getRankInfos().values()) {
                        aggRanking.updateScore(info);
                    }
                }

                return aggRanking;
            }
        });
    }
}
