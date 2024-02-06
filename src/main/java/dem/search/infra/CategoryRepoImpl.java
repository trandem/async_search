package dem.search.infra;

import dem.search.repo.CategoryRepo;
import dem.search.repo.dto.CategoryRequest;
import dem.search.repo.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepoImpl implements CategoryRepo {

    private final WebClient categoryClient;

    @Override
    public Mono<CategoryResponse> predictCategories(CategoryRequest categoryRequest) {
        // call category service for get categories
//        return categoryClient.get().uri("/v1/categories")
//                .attribute("q", categoryRequest.getQuery())
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve().bodyToMono(CategoryResponse.class);
        return Mono.just(new CategoryResponse(List.of(0, 1)));
    }
}
