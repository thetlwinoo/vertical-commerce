package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Orders;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrdersExtendRepository extends OrdersRepository {

    List<Orders> findAllByCustomerIdOrderByLastEditedWhenDesc(Long id);

    Integer countAllByCustomerId(Long id);

    @Query(value = "SELECT * FROM get_order_packages(:orderId)", nativeQuery = true)
    String getOrderPackageDetails(@Param("orderId") Long orderId);

    @Query(value = "SELECT * FROM get_orderpackages_ids_by_filter(:customerId,:completedReview)",nativeQuery = true)
    List<Long> getOrderPackagesIdsByFilter(@Param("customerId") Long customerId, @Param("completedReview") Boolean completedReview);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "CALL sp_check_order_cancel(:orderLineId);", nativeQuery = true)
    Integer checkOrderCancel(@Param("orderLineId") Long orderLineId);
}
