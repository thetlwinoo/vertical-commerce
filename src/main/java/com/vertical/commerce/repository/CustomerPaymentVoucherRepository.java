package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CustomerPaymentVoucher;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerPaymentVoucher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerPaymentVoucherRepository extends JpaRepository<CustomerPaymentVoucher, Long>, JpaSpecificationExecutor<CustomerPaymentVoucher> {
}
