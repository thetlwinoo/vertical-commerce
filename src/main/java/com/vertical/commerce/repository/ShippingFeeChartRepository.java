package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ShippingFeeChart;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ShippingFeeChart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShippingFeeChartRepository extends JpaRepository<ShippingFeeChart, Long>, JpaSpecificationExecutor<ShippingFeeChart> {
}
