package dem.search.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchProductEntity {
    private Long id;
    private String name;
    private String origin;

    private Double rankingScore;
}
