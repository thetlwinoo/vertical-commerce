package com.vertical.commerce.repository;

import com.vertical.commerce.domain.OrderPackages;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrderPackages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderPackagesRepository extends JpaRepository<OrderPackages, Long>, JpaSpecificationExecutor<OrderPackages> {
}
