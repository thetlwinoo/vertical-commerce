package com.vertical.commerce.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoicesExtendRepository extends InvoicesRepository {
    @Query(value = "CALL invoice_customer_orders(:orderId,:packedByPersonId,:invoicedByPersonId);", nativeQuery = true)
    void invoiceCustomerOrders(@Param("orderId") Long orderId,@Param("packedByPersonId") Long packedByPersonId, @Param("invoicedByPersonId") Long invoicedByPersonId);
}
