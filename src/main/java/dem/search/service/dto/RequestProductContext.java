package dem.search.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestProductContext implements Cloneable {
    private String query;
    private List<Integer> brandIds;
    private List<Integer> origins;
    private List<Integer> categories;
    private Integer sortBy;

    private Integer from;
    private Integer size;

    public RequestProductContext duplicate() {
        return null;
    }

    public String BuildCachingKey() {
        // cache queries result, for paging or something
        return "";
    }

    @Override
    public RequestProductContext clone() {
        try {
            RequestProductContext clone = (RequestProductContext) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
