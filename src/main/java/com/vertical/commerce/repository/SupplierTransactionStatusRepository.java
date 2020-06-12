package com.vertical.commerce.repository;

import com.vertical.commerce.domain.SupplierTransactionStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SupplierTransactionStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierTransactionStatusRepository extends JpaRepository<SupplierTransactionStatus, Long>, JpaSpecificationExecutor<SupplierTransactionStatus> {
}
