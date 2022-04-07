package com.api.springquery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.function.Consumer;

@Getter
@Setter
@AllArgsConstructor
public class QueryCriteriaClient implements Consumer<SearchCriteria> {

    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root r;

    @Override
    public void accept(SearchCriteria param) {
        if (param.getSearchOperation().equalsIgnoreCase(">")) {
            predicate = builder.and(predicate, builder
                    .greaterThanOrEqualTo(r.get(param.getSearchKey()),
                            param.getSearchValue().toString()));
        } else if (param.getSearchOperation().equalsIgnoreCase("<")) {
            predicate = builder.and(predicate, builder.lessThanOrEqualTo(
                    r.get(param.getSearchKey()), param.getSearchValue().toString()));
        } else if (param.getSearchOperation().equalsIgnoreCase(":")) {
            if (r.get(param.getSearchKey()).getJavaType() == String.class) {
                predicate = builder.and(predicate, builder.like(
                        r.get(param.getSearchKey()), "%" + param.getSearchValue() + "%"));
            } else {
                predicate = builder.and(predicate, builder.equal(
                        r.get(param.getSearchKey()), param.getSearchValue()));
            }
        }
    }
}
