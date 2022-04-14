package com.zhangkem.coopcycle.repository;

import com.zhangkem.coopcycle.domain.OrderContent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrderContent entity.
 */
@Repository
public interface OrderContentRepository extends OrderContentRepositoryWithBagRelationships, JpaRepository<OrderContent, Long> {
    default Optional<OrderContent> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<OrderContent> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<OrderContent> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
