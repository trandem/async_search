package dem.search.service.ranking;

import dem.search.entity.RankingResponse;
import dem.search.service.dto.RankingContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class FastDeliveryRaking implements ProductRanking {
    @Override
    public Mono<RankingResponse> sortProduct(RankingContext rankingContext) {

        return findDeliveryInfo(rankingContext)
                .map(deliveries -> {
                    var response = new RankingResponse();

                    for (var deliveryInfo : deliveries) {
                        var info = new RankingResponse.RankInfo(deliveryInfo.getId(), 0.0);
                        if (Boolean.TRUE.equals(deliveryInfo.getCanFastDelivery())){
                            info.setScore(3.0);
                        }
                        response.appendInfo(info);
                    }
                    return response;
                });
    }

    private Mono<List<DeliveryInfo>> findDeliveryInfo(RankingContext rankingContext) {
        // hard code
        var deliveryInfoList = new ArrayList<DeliveryInfo>(rankingContext.getProductEntities().size());
        for (var entity : rankingContext.getProductEntities()) {
            var info = new DeliveryInfo(entity.getId(), false);
            // hashcode
            if (entity.getId() % 2 == 0) {
                info.setCanFastDelivery(true);
            }
            deliveryInfoList.add(info);
        }
        return Mono.just(deliveryInfoList);
    }

    @Data
    @AllArgsConstructor
    public static class DeliveryInfo {
        private Long id;
        private Boolean canFastDelivery;
    }
}
