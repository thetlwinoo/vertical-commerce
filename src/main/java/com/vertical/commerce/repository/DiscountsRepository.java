package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Discounts;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Discounts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountsRepository extends JpaRepository<Discounts, Long>, JpaSpecificationExecutor<Discounts> {
}
