package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ShippingFeeCalculated;
import com.vertical.commerce.domain.ShippingFeeChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ShippingFeeChartExtendRepository extends JpaRepository<ShippingFeeCalculated,Long> {
    @Query(value = "SELECT * FROM calculate_shipping_fee(:shoppingCartId)", nativeQuery = true)
    Optional<List<ShippingFeeCalculated>> getCalculateShippingFee(@Param("shoppingCartId") Long shoppingCartId);
}
