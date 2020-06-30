package com.vertical.commerce.repository;

import com.vertical.commerce.domain.OrderPackages;
import com.vertical.commerce.domain.ProductCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderPackagesExtendRepository extends OrderPackagesRepository {
    Optional<OrderPackages> findOrderPackagesBySupplierId(Long id);

    @Query(value = "SELECT * FROM order_packages WHERE order_id = :orderId AND supplierId = :supplierId", nativeQuery = true)
    List<OrderPackages> findOrderPackages(@Param("orderId") Long orderId, @Param("supplierId") Long supplierId);
}
