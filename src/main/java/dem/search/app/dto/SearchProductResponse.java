package dem.search.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchProductResponse {
    private List<ProductInfo> info;

    @Data
    public static class ProductInfo {
        private Long id;
        private String name;
        private String origin;
    }
}
