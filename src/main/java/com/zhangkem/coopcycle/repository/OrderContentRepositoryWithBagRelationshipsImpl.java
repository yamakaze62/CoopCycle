package com.zhangkem.coopcycle.repository;

import com.zhangkem.coopcycle.domain.OrderContent;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class OrderContentRepositoryWithBagRelationshipsImpl implements OrderContentRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<OrderContent> fetchBagRelationships(Optional<OrderContent> orderContent) {
        return orderContent.map(this::fetchProducts);
    }

    @Override
    public Page<OrderContent> fetchBagRelationships(Page<OrderContent> orderContents) {
        return new PageImpl<>(
            fetchBagRelationships(orderContents.getContent()),
            orderContents.getPageable(),
            orderContents.getTotalElements()
        );
    }

    @Override
    public List<OrderContent> fetchBagRelationships(List<OrderContent> orderContents) {
        return Optional.of(orderContents).map(this::fetchProducts).orElse(Collections.emptyList());
    }

    OrderContent fetchProducts(OrderContent result) {
        return entityManager
            .createQuery(
                "select orderContent from OrderContent orderContent left join fetch orderContent.products where orderContent is :orderContent",
                OrderContent.class
            )
            .setParameter("orderContent", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<OrderContent> fetchProducts(List<OrderContent> orderContents) {
        return entityManager
            .createQuery(
                "select distinct orderContent from OrderContent orderContent left join fetch orderContent.products where orderContent in :orderContents",
                OrderContent.class
            )
            .setParameter("orderContents", orderContents)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
