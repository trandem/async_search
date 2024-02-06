package dem.search.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchProductRequest {
    private String query;
    private List<Integer> brandIds;
    private List<Integer> origins;
    private Integer sortBy;
    private Integer from = 0;
    private Integer size = 20;
}
