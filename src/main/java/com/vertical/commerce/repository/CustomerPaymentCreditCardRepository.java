package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CustomerPaymentCreditCard;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CustomerPaymentCreditCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerPaymentCreditCardRepository extends JpaRepository<CustomerPaymentCreditCard, Long>, JpaSpecificationExecutor<CustomerPaymentCreditCard> {
}
