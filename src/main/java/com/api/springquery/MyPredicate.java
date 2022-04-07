package com.api.springquery;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import lombok.Data;

import static org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric;

@Data
public class MyPredicate {
    private SearchCriteria criteria;
    public BooleanExpression getPredicate() {
        PathBuilder<AppUser> entityPath = new PathBuilder<>(AppUser.class, "user");

        if (isNumeric(criteria.getSearchValue().toString())) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getSearchKey(), Integer.class);
            int value = Integer.parseInt(criteria.getSearchValue().toString());
            switch (criteria.getSearchOperation()) {
                case ":":
                    return path.eq(value);
                case ">":
                    return path.goe(value);
                case "<":
                    return path.loe(value);
            }
        }
        else {
            StringPath path = entityPath.getString(criteria.getSearchKey());
            if (criteria.getSearchOperation().equalsIgnoreCase(":")) {
                return path.containsIgnoreCase(criteria.getSearchValue().toString());
            }
        }
        return null;
    }
}
