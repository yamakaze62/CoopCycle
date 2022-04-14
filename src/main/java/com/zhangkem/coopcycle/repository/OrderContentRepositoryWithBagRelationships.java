package com.zhangkem.coopcycle.repository;

import com.zhangkem.coopcycle.domain.OrderContent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface OrderContentRepositoryWithBagRelationships {
    Optional<OrderContent> fetchBagRelationships(Optional<OrderContent> orderContent);

    List<OrderContent> fetchBagRelationships(List<OrderContent> orderContents);

    Page<OrderContent> fetchBagRelationships(Page<OrderContent> orderContents);
}
