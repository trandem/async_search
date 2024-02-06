package dem.search.repo;

import dem.search.repo.dto.CategoryRequest;
import dem.search.repo.dto.CategoryResponse;
import reactor.core.publisher.Mono;

public interface CategoryRepo {
    Mono<CategoryResponse> predictCategories(CategoryRequest categoryRequest);
}
