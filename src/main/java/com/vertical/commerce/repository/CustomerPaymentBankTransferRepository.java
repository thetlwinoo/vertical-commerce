package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CustomerPaymentBankTransfer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerPaymentBankTransfer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerPaymentBankTransferRepository extends JpaRepository<CustomerPaymentBankTransfer, Long>, JpaSpecificationExecutor<CustomerPaymentBankTransfer> {
}
