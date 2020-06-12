package com.vertical.commerce.repository;

import com.vertical.commerce.domain.DiscountDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DiscountDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountDetailsRepository extends JpaRepository<DiscountDetails, Long>, JpaSpecificationExecutor<DiscountDetails> {
}
