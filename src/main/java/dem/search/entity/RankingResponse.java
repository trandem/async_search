package dem.search.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingResponse {

    private Map<Long, RankInfo> rankInfos = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RankInfo {
        private Long id;
        private Double score;
    }

    public void appendInfo(RankInfo info) {
        rankInfos.put(info.getId(), info);
    }

    public void updateScore(RankInfo info) {
        rankInfos.compute(info.getId(), (k, v) -> {
            if (v == null) {
                return info;
            }
            var newScore = info.getScore() + v.getScore();
            v.setScore(newScore);

            return v;
        });
    }
}
