package dem.search.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrichmentContext {
    private String query;
    private List<Integer> brandIds;
    private List<Integer> origins;
    private List<Integer> categories;
    private Integer sortBy;
}
