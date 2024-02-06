package dem.search.app.dto;

import dem.search.entity.SearchProductEntity;
import dem.search.service.dto.RequestProductContext;
import org.mapstruct.Mapper;
import org.springframework.data.convert.TypeMapper;

import java.util.List;


@Mapper(componentModel = "spring", uses = TypeMapper.class)
public interface SearchControllerMapper {
    RequestProductContext convertFrom(SearchProductRequest request);

    List<SearchProductResponse.ProductInfo> convertFrom(List<SearchProductEntity> entities);
}
