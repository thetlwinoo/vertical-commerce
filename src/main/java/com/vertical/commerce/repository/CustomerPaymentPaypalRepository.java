package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CustomerPaymentPaypal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerPaymentPaypal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerPaymentPaypalRepository extends JpaRepository<CustomerPaymentPaypal, Long>, JpaSpecificationExecutor<CustomerPaymentPaypal> {
}
