package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoicesExtendRepository extends JpaRepository<Invoices,Long> {
    @Query(value = "CALL invoice_customer_orders(:orderPackageId, :packedByPersonId, :invoicedByPersonId, null);", nativeQuery = true)
    String invoiceCustomerOrders(@Param("orderPackageId") Long orderPackageId,@Param("packedByPersonId") Long packedByPersonId, @Param("invoicedByPersonId") Long invoicedByPersonId);
}
