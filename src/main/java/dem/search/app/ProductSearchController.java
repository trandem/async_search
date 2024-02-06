package dem.search.app;

import dem.search.app.dto.SearchControllerMapper;
import dem.search.app.dto.SearchProductRequest;
import dem.search.app.dto.SearchProductResponse;
import dem.search.service.SearchProductFlow;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProductSearchController {
    private final SearchProductFlow searchProductManager;
    private final SearchControllerMapper mapper;

    @PostMapping("/find/product")
    public Mono<SearchProductResponse> findProduct(@RequestBody SearchProductRequest body) {
        var context = mapper.convertFrom(body);
        return searchProductManager.findProduct(context)
                .map(listProduct -> {
                    var infoOutput = mapper.convertFrom(listProduct);
                    var searchResponse = new SearchProductResponse();
                    searchResponse.setInfo(infoOutput);
                    return searchResponse;
                });

    }
}
