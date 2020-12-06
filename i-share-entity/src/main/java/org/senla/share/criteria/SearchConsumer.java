package org.senla.share.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.function.Consumer;

public class SearchConsumer implements Consumer<SearchCriteria> {
    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root root;

    public SearchConsumer(Predicate predicate, CriteriaBuilder builder, Root root) {
        super();
        this.predicate = predicate;
        this.builder = builder;
        this.root = root;
    }

    @Override
    public void accept(SearchCriteria param) {
        if (param.getOperation().equalsIgnoreCase(">")) {
            predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase("<")) {
            predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase(":")) {
            if (root.get(param.getKey()).getJavaType() == String.class) {
                predicate = builder.and(predicate, builder.like(root.get(param.getKey()), "%" + param.getValue() + "%"));
            } else if (root.get(param.getKey()).getJavaType() == Boolean.class) {
                predicate = builder.and(predicate, builder.equal(root.get(param.getKey()), param.getValue().equals("true") ? 1 : 0));
            } else {
                predicate = builder.and(predicate, builder.equal(root.get(param.getKey()), param.getValue()));
            }
        }
    }

    public Predicate getPredicate() {
        return predicate;
    }
}
