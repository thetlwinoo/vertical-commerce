package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CustomerPaymentCreditCardExtended;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerPaymentCreditCardExtended entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerPaymentCreditCardExtendedRepository extends JpaRepository<CustomerPaymentCreditCardExtended, Long>, JpaSpecificationExecutor<CustomerPaymentCreditCardExtended> {
}
