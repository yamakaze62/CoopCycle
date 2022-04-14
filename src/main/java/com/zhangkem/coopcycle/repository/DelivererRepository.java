package com.zhangkem.coopcycle.repository;

import com.zhangkem.coopcycle.domain.Deliverer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Deliverer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DelivererRepository extends JpaRepository<Deliverer, Long> {}
