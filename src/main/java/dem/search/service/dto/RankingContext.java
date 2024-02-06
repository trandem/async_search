package dem.search.service.dto;

import dem.search.entity.SearchProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RankingContext {
    private List<SearchProductEntity> productEntities;
    private Long userId;
}
